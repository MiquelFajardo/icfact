package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.*;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.RepositoryGeografia;

import java.util.List;
import java.util.Optional;

public class PoblacioRepository implements RepositoryGeografia<Poblacio, Long> {

    @Override
    public void guardar(Poblacio poblacio) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(poblacio);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Poblacio poblacio) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(poblacio);
            tx.commit();
        }
    }

    @Override
    public Optional<Poblacio> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Poblacio.class, id));
        }
    }

    @Override
    public List<Poblacio> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Poblacio ORDER BY nom", Poblacio.class).list();
        }
    }

    public Optional<Poblacio> buscarPerNomIProvincia(String nom, Provincia provincia) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Poblacio WHERE nom = :nom AND provincia = :provincia",
                            Poblacio.class)
                    .setParameter("nom", nom)
                    .setParameter("provincia", provincia)
                    .uniqueResultOptional();
        }
    }

    public List<Poblacio> buscarPerProvincia(Provincia provincia) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Poblacio WHERE provincia = :provincia ORDER BY nom",
                            Poblacio.class)
                    .setParameter("provincia", provincia)
                    .list();
        }
    }
}
