package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.RepositoryGeografia;

import java.util.List;
import java.util.Optional;

public class AdrecaRepository implements RepositoryGeografia<Adreca, Long> {
    @Override
    public void guardar(Adreca adreca) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(adreca);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Adreca adreca) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(adreca);
            tx.commit();
        }
    }

    @Override
    public Optional<Adreca> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Adreca.class, id));
        }
    }

    @Override
    public List<Adreca> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Adreca ORDER BY carrer", Adreca.class).list();
        }
    }

    public List<Adreca> buscarPerPoblacio(Poblacio poblacio) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Adreca WHERE poblacio = :poblacio ORDER BY carrer", Adreca.class)
                    .setParameter("poblacio", poblacio)
                    .list();
        }
    }
}
