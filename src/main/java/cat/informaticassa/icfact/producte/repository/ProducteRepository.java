package cat.informaticassa.icfact.producte.repository;

import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.producte.model.Producte;

import java.util.List;
import java.util.Optional;

public class ProducteRepository extends AbstractActivableRepository<Producte, Long> {

    @Override
    protected String ordrePerDefecte() {
        return "nom";
    }

    @Override
    public Optional<Producte> buscarPerId(Long id) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                SELECT p
                FROM Producte p
                LEFT JOIN FETCH p.iva
                WHERE p.id = :id
                """, Producte.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Producte> buscarTots() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Producte p
                    LEFT JOIN FETCH p.iva
                    ORDER BY p.nom
                    """, Producte.class)
                    .list();
        }
    }

    @Override
    public List<Producte> buscarInactius() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Producte p
                    LEFT JOIN FETCH p.iva
                    WHERE p.actiu = false
                    ORDER BY p.nom
                    """, Producte.class)
                    .list();
        }
    }

    public Optional<Producte> buscarPerCodi(String codi) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT p
                    FROM Producte p
                    LEFT JOIN FETCH p.iva
                    WHERE p.codi = :codi
                    AND p.actiu = true
                    """, Producte.class)
                    .setParameter("codi", codi)
                    .uniqueResultOptional();
        }
    }

    public List<Producte> buscarPerNom(String nom) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Producte p
                    LEFT JOIN FETCH p.iva
                    WHERE lower(p.nom) LIKE lower(:nom)
                    AND p.actiu = true
                    ORDER BY p.nom
                    """, Producte.class)
                    .setParameter("nom", "%" + nom + "%")
                    .list();
        }
    }

    public List<Producte> buscarActius() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Producte p
                    LEFT JOIN FETCH p.iva
                    WHERE p.actiu = true
                    ORDER BY p.nom
                    """, Producte.class)
                    .list();
        }
    }

    public List<Producte> buscar(String text, boolean actius, boolean inactius) {
        try (var session = obrirSessio()) {
            StringBuilder hql = new StringBuilder("""
                    SELECT DISTINCT p
                    FROM Producte p
                    LEFT JOIN FETCH p.iva
                    WHERE (
                        lower(p.codi) LIKE :text
                        OR lower(p.nom) LIKE :text
                        OR lower(coalesce(p.descripcio,'')) LIKE :text
                    )
                    """);
            if (actius && !inactius) {
                hql.append(" AND p.actiu = true");
            } else if (!actius && inactius) {
                hql.append(" AND p.actiu = false");
            }
            hql.append(" ORDER BY p.nom");
            return session.createQuery(hql.toString(), Producte.class)
                    .setParameter("text", "%" + text.toLowerCase() + "%")
                    .list();
        }
    }
}