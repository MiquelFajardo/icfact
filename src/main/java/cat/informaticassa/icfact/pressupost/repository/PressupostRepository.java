package cat.informaticassa.icfact.pressupost.repository;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.pressupost.model.Pressupost;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PressupostRepository extends AbstractActivableRepository<Pressupost, Long> {

    @Override
    protected String ordrePerDefecte() {
        return "data DESC";
    }

    @Override
    public Optional<Pressupost> buscarPerId(Long id) {
        return buscarPerIdAmbLinies(id);
    }

    public Optional<Pressupost> buscarPerIdIncloentInactius(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Pressupost p
                    LEFT JOIN FETCH p.client
                    LEFT JOIN FETCH p.formaPagament
                    LEFT JOIN FETCH p.linies l
                    LEFT JOIN FETCH l.producte
                    LEFT JOIN FETCH l.iva
                    WHERE p.id = :id
                    """, Pressupost.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public Optional<Pressupost> buscarPerNumero(String numero) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Pressupost p
                    LEFT JOIN FETCH p.client
                    LEFT JOIN FETCH p.formaPagament
                    LEFT JOIN FETCH p.linies l
                    LEFT JOIN FETCH l.producte
                    LEFT JOIN FETCH l.iva
                    WHERE p.numero = :numero
                    AND p.actiu = true
                    """, Pressupost.class)
                    .setParameter("numero", numero)
                    .uniqueResultOptional();
        }
    }

    public List<Pressupost> buscarPerData(LocalDate data) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pressupost
                    WHERE data = :data
                    AND actiu = true
                    ORDER BY numero
                    """, Pressupost.class)
                    .setParameter("data", data)
                    .list();
        }
    }

    public Optional<Pressupost> buscarPerIdAmbLinies(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Pressupost p
                    LEFT JOIN FETCH p.client
                    LEFT JOIN FETCH p.formaPagament
                    LEFT JOIN FETCH p.linies l
                    LEFT JOIN FETCH l.producte
                    LEFT JOIN FETCH l.iva
                    WHERE p.id = :id
                    AND p.actiu = true
                    """, Pressupost.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public long obtenirSeguentNumero(int any) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            String prefix = "P" + any;
            Long ultimNumero = session.createQuery("""
                    SELECT MAX(CAST(SUBSTRING(p.numero, 6) AS long))
                    FROM Pressupost p
                    WHERE p.numero LIKE :prefix
                    """, Long.class)
                    .setParameter("prefix", prefix + "%")
                    .uniqueResult();
            return ultimNumero == null ? 1 : ultimNumero + 1;
        }
    }

    @Override
    public List<Pressupost> buscarTots() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Pressupost p
                    LEFT JOIN FETCH p.client
                    LEFT JOIN FETCH p.formaPagament
                    ORDER BY p.data DESC, p.numero DESC
                    """, Pressupost.class)
                    .list();
        }
    }

    public List<Pressupost> buscarActius() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Pressupost p
                    LEFT JOIN FETCH p.client
                    LEFT JOIN FETCH p.formaPagament
                    WHERE p.actiu = true
                    ORDER BY p.data DESC, p.numero DESC
                    """, Pressupost.class)
                    .list();
        }
    }

    @Override
    public List<Pressupost> buscarInactius() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT DISTINCT p
                    FROM Pressupost p
                    LEFT JOIN FETCH p.client
                    LEFT JOIN FETCH p.formaPagament
                    WHERE p.actiu = false
                    ORDER BY p.data DESC, p.numero DESC
                    """, Pressupost.class)
                    .list();
        }
    }

    public List<Pressupost> buscar(String text, boolean actius, boolean inactius, Client client) {
        try (var session = obrirSessio()) {
            StringBuilder hql = new StringBuilder("""
            SELECT DISTINCT p
            FROM Pressupost p
            LEFT JOIN FETCH p.client
            LEFT JOIN FETCH p.formaPagament
            WHERE (
                lower(p.numero) LIKE :text
                OR lower(p.client.nom) LIKE :text
                OR lower(coalesce(p.observacions,'')) LIKE :text
            )
            """);

            if (client != null) {
                hql.append(" AND p.client.id = :clientId");
            }

            if (actius && !inactius) {
                hql.append(" AND p.actiu = true");
            } else if (!actius && inactius) {
                hql.append(" AND p.actiu = false");
            }

            hql.append(" ORDER BY p.data DESC, p.numero DESC");

            var query = session.createQuery(hql.toString(), Pressupost.class)
                    .setParameter("text", "%" + text.toLowerCase() + "%");

            if (client != null) {
                query.setParameter("clientId", client.getId());
            }
            return query.list();
        }
    }
}