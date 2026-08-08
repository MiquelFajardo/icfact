package cat.informaticassa.icfact.factura.repository;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FacturaRepository extends AbstractActivableRepository<Factura, Long> {

    @Override
    protected String ordrePerDefecte() {
        return "data DESC";
    }

    @Override
    public void guardar(Factura factura) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(factura);
            tx.commit();
        }
    }

    @Override
    public Optional<Factura> buscarPerId(Long id) {
        return buscarPerIdAmbLinies(id);
    }

    public Optional<Factura> buscarPerIdIncloentInactius(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client c
                    LEFT JOIN FETCH c.adreca a
                    LEFT JOIN FETCH a.poblacio
                    LEFT JOIN FETCH f.formaPagament
                    LEFT JOIN FETCH f.linies l
                    LEFT JOIN FETCH l.iva
                    LEFT JOIN FETCH l.producte
                    WHERE f.id = :id
                    """, Factura.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public Optional<Factura> buscarPerIdAmbLinies(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client c
                    LEFT JOIN FETCH c.adreca a
                    LEFT JOIN FETCH a.poblacio
                    LEFT JOIN FETCH f.formaPagament
                    LEFT JOIN FETCH f.linies l
                    LEFT JOIN FETCH l.iva
                    LEFT JOIN FETCH l.producte
                    WHERE f.id = :id
                    AND f.actiu = true
                    """, Factura.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public Optional<Factura> buscarPerNumero(String numero) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client c
                    LEFT JOIN FETCH c.adreca a
                    LEFT JOIN FETCH a.poblacio
                    LEFT JOIN FETCH f.formaPagament
                    LEFT JOIN FETCH f.linies l
                    LEFT JOIN FETCH l.iva
                    LEFT JOIN FETCH l.producte
                    WHERE f.numero = :numero
                    AND f.actiu = true
                    """, Factura.class)
                    .setParameter("numero", numero)
                    .uniqueResultOptional();
        }
    }

    public List<Factura> buscarPerData(LocalDate data) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Factura
                    WHERE data = :data
                    AND actiu = true
                    ORDER BY numero
                    """, Factura.class)
                    .setParameter("data", data)
                    .list();
        }
    }

    public List<Factura> buscarTotes() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client
                    LEFT JOIN FETCH f.formaPagament
                    ORDER BY f.data DESC, f.numero DESC
                    """, Factura.class)
                    .list();
        }
    }

    public List<Factura> buscarActives() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client
                    LEFT JOIN FETCH f.formaPagament
                    WHERE f.actiu = true
                    ORDER BY f.data DESC, f.numero DESC
                    """, Factura.class)
                    .list();
        }
    }

    public List<Factura> buscarInactives() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.client
                    LEFT JOIN FETCH f.formaPagament
                    WHERE f.actiu = false
                    ORDER BY f.data DESC, f.numero DESC
                    """, Factura.class)
                    .list();
        }
    }

    public List<Factura> buscar(
            String text,
            boolean actives,
            boolean inactives,
            Client client) {

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

            var query = session.createQuery(hql.toString(), Factura.class)
                    .setParameter("text", "%" + text.toLowerCase() + "%");

            if (client != null) {
                query.setParameter("clientId", client.getId());
            }

            return query.list();
        }
    }

    public long obtenirSeguentNumero(int any) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {

            String prefix = "F" + any;

            Long ultimNumero = session.createQuery("""
                    SELECT MAX(CAST(SUBSTRING(f.numero, 6) AS long))
                    FROM Factura f
                    WHERE f.numero LIKE :prefix
                    """, Long.class)
                    .setParameter("prefix", prefix + "%")
                    .uniqueResult();

            return ultimNumero == null ? 1 : ultimNumero + 1;
        }
    }
}