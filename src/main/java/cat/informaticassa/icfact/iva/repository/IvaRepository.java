package cat.informaticassa.icfact.iva.repository;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;
import cat.informaticassa.icfact.iva.model.Iva;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class IvaRepository implements Repository<Iva, Long> {

    @Override
    public void guardar(Iva iva) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(iva);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Iva iva) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(iva);
            tx.commit();
        }
    }

    @Override
    public Optional<Iva> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Iva.class, id));
        }
    }

    @Override
    public List<Iva> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Iva ORDER BY percentatge",
                    Iva.class
            ).list();

        }
    }

    @Override
    public void eliminar(Iva iva) {
        iva.setActiu(false);
        actualitzar(iva);

    }

    @Override
    public void activar(Iva iva) {
        iva.setActiu(true);
        actualitzar(iva);
    }

    public Optional<Iva> buscarPerPercentatge(BigDecimal percentatge) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Iva WHERE percentatge = :percentatge",
                            Iva.class
                    )
                    .setParameter("percentatge", percentatge)
                    .uniqueResultOptional();
        }
    }

    public List<Iva> buscarActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Iva WHERE actiu = true ORDER BY percentatge",
                    Iva.class
            ).list();
        }
    }

    public List<Iva> buscarInactius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Iva WHERE actiu = false ORDER BY percentatge",
                    Iva.class
            ).list();
        }
    }
}