package cat.informaticassa.icfact.pressupost.repository;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;


import java.util.List;
import java.util.Optional;

public class LiniaPressupostRepository implements Repository<LiniaPressupost, Long> {

    @Override
    public void guardar(LiniaPressupost linia) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(linia);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(LiniaPressupost linia) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(linia);
            tx.commit();
        }
    }

    @Override
    public Optional<LiniaPressupost> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(LiniaPressupost.class, id));
        }
    }

    @Override
    public List<LiniaPressupost> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM LiniaPressupost ORDER BY id",
                    LiniaPressupost.class
            ).list();
        }
    }

    public List<LiniaPressupost> buscarTotsActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM LiniaPressupost WHERE actiu = true ORDER BY id",
                    LiniaPressupost.class
            ).list();

        }
    }

    @Override
    public void eliminar(LiniaPressupost linia) {
        linia.setActiu(false);
        actualitzar(linia);
    }

    @Override
    public void activar(LiniaPressupost linia) {
        linia.setActiu(true);
        actualitzar(linia);
    }

    public List<LiniaPressupost> buscarPerPressupost(Long pressupostId) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM LiniaPressupost
                    WHERE pressupost.id = :id
                    AND actiu = true
                    ORDER BY id
                    """, LiniaPressupost.class)
                    .setParameter("id", pressupostId)
                    .list();
        }
    }
}
