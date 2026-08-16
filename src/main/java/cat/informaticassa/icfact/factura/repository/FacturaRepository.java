package cat.informaticassa.icfact.factura.repository;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FacturaRepository extends AbstractActivableRepository<Factura, Long> {

    private static final String JOIN_FETCH_COMPLET = """
            SELECT DISTINCT f
            FROM Factura f
            LEFT JOIN FETCH f.client c
            LEFT JOIN FETCH c.adreca a
            LEFT JOIN FETCH a.poblacio
            LEFT JOIN FETCH f.formaPagament
            LEFT JOIN FETCH f.linies l
            LEFT JOIN FETCH l.iva
            LEFT JOIN FETCH l.producte
            """;

    @Override
    protected String ordrePerDefecte() {
        return "data DESC";
    }

    @Override
    public Optional<Factura> buscarPerId(Long id) {
        return buscarPerIdAmbLinies(id);
    }

    public Optional<Factura> buscarPerIdIncloentInactius(Long id) {
        try (var session = obrirSessio()) {
            return session.createQuery(JOIN_FETCH_COMPLET + "WHERE f.id = :id", Factura.class).setParameter("id", id).uniqueResultOptional();
        }
    }

    public Optional<Factura> buscarPerIdAmbLinies(Long id) {
        try (var session = obrirSessio()) {
            return session.createQuery( JOIN_FETCH_COMPLET + """
                            WHERE f.id = :id
                            AND f.actiu = true
                            """,
                            Factura.class).setParameter("id", id).uniqueResultOptional();
        }
    }

    public List<Factura> buscar(String text, boolean actives, boolean inactives, Client client) {
        try (var session = obrirSessio()) {
            StringBuilder hql = new StringBuilder("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client
                    LEFT JOIN FETCH f.formaPagament
                    WHERE (
                        lower(f.numero) LIKE :text
                        OR lower(f.client.nom) LIKE :text
                        OR lower(coalesce(f.observacions, '')) LIKE :text
                    )
                    """);
            if (client != null) {
                hql.append(" AND f.client.id = :clientId");
            }

            if (actives && !inactives) {
                hql.append(" AND f.actiu = true");
            } else if (!actives && inactives) {
                hql.append(" AND f.actiu = false");
            }

            hql.append(" ORDER BY f.data DESC, f.numero DESC");
            var query = session.createQuery(hql.toString(), Factura.class).setParameter("text", "%" + text.toLowerCase() + "%");
            if (client != null) {
                query.setParameter("clientId", client.getId());
            }
            return query.list();
        }
    }

    public List<Factura> buscarPendents() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client
                    LEFT JOIN FETCH f.formaPagament
                    WHERE f.actiu = true
                    AND f.estat = cat.informaticassa.icfact.factura.model.EstatFactura.EMESA
                    AND f.total > COALESCE(
                        (
                            SELECT SUM(p.importPagat)
                            FROM Pagament p
                            WHERE p.factura = f
                            AND p.actiu = true
                        ),
                        0
                    )
                    ORDER BY f.data DESC, f.numero DESC
                    """, Factura.class).list();
        }
    }

    public List<Factura> buscarPeriodePerInforme(
            LocalDate desDe,
            LocalDate finsA) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client
                    LEFT JOIN FETCH f.linies l
                    LEFT JOIN FETCH l.iva
                    WHERE f.actiu = true
                    AND f.estat IN (
                        cat.informaticassa.icfact.factura.model.EstatFactura.EMESA,
                        cat.informaticassa.icfact.factura.model.EstatFactura.COBRADA
                    )
                    AND f.data BETWEEN :desDe AND :finsA
                    ORDER BY f.data DESC, f.numero DESC
                    """, Factura.class).setParameter("desDe", desDe).setParameter("finsA", finsA).list();
        }
    }
}