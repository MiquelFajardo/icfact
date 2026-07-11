package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.RepositoryGeografia;

import java.util.List;
import java.util.Optional;

public class ProvinciaRepository implements RepositoryGeografia<Provincia, Long> {

    @Override
    public void guardar(Provincia provincia) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(provincia);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Provincia provincia) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(provincia);
            tx.commit();
        }
    }

    @Override
    public Optional<Provincia> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Provincia.class, id));
        }
    }

    public Optional<Provincia> buscarPerCodi(String codi) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Provincia WHERE codi = :codi", Provincia.class)
                    .setParameter("codi", codi)
                    .uniqueResultOptional();
        }
    }

    public List<Provincia> buscarPerPais(Pais pais) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Provincia WHERE pais = :pais ORDER BY nom", Provincia.class)
                    .setParameter("pais", pais)
                    .list();
        }
    }

    @Override
    public List<Provincia> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Provincia", Provincia.class).list();
        }
    }
}
