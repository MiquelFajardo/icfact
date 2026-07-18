package cat.informaticassa.icfact.formaPagament.repository;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;

import java.util.List;
import java.util.Optional;

public class FormaPagamentRepository implements Repository<FormaPagament, Long> {

    @Override
    public void guardar(FormaPagament formaPagament) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(formaPagament);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(FormaPagament formaPagament) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(formaPagament);
            tx.commit();
        }
    }

    @Override
    public Optional<FormaPagament> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM FormaPagament
                    WHERE id = :id
                    AND actiu = true
                    """, FormaPagament.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public Optional<FormaPagament> buscarPerIdIncloentInactius(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM FormaPagament
                    WHERE id = :id
                    """, FormaPagament.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<FormaPagament> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM FormaPagament
                    WHERE actiu = true
                    ORDER BY nom
                    """, FormaPagament.class)
                    .list();
        }
    }

    public List<FormaPagament> buscarActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM FormaPagament
                    WHERE actiu = true
                    ORDER BY nom
                    """, FormaPagament.class)
                    .list();
        }
    }

    public List<FormaPagament> buscarInactius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM FormaPagament
                    WHERE actiu = false
                    ORDER BY nom
                    """, FormaPagament.class)
                    .list();
        }
    }

    public Optional<FormaPagament> buscarPerNom(String nom) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM FormaPagament
                    WHERE nom = :nom
                    AND actiu = true
                    """, FormaPagament.class)
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }

    @Override
    public void eliminar(FormaPagament formaPagament) {
        formaPagament.setActiu(false);
        actualitzar(formaPagament);
    }

    @Override
    public void activar(FormaPagament formaPagament) {
        formaPagament.setActiu(true);
        actualitzar(formaPagament);
    }
}