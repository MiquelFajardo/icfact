package cat.informaticassa.icfact.pressupost.repository;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;
import cat.informaticassa.icfact.pressupost.model.Pressupost;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PressupostRepository implements Repository<Pressupost, Long> {

    @Override
    public void guardar(Pressupost pressupost) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(pressupost);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Pressupost pressupost) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(pressupost);
            tx.commit();
        }
    }

    @Override
    public Optional<Pressupost> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pressupost
                    WHERE id = :id
                    AND actiu = true
                    """, Pressupost.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Pressupost> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pressupost
                    WHERE actiu = true
                    ORDER BY data DESC
                    """, Pressupost.class)
                    .list();
        }
    }

    public List<Pressupost> buscarInactius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pressupost
                    WHERE actiu = false
                    ORDER BY data DESC
                    """, Pressupost.class)
                    .list();
        }
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

    @Override
    public void eliminar(Pressupost pressupost) {
        pressupost.setActiu(false);
        actualitzar(pressupost);
    }

    @Override
    public void activar(Pressupost pressupost) {
        pressupost.setActiu(true);
        actualitzar(pressupost);
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