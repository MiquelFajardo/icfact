package cat.informaticassa.icfact.pressupost.repository;

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
                LEFT JOIN FETCH p.linies l
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
                    LEFT JOIN FETCH p.linies l
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
                    SELECT MAX(CAST(SUBSTRING(p.numero, 5) AS long))
                    FROM Pressupost p
                    WHERE p.numero LIKE :prefix
                    """, Long.class)
                    .setParameter("prefix", prefix + "%")
                    .uniqueResult();

            return ultimNumero == null ? 1 : ultimNumero + 1;
        }
    }
}