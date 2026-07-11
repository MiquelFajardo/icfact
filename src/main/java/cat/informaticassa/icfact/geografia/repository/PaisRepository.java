package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;

import cat.informaticassa.icfact.infraestructura.repository.RepositoryGeografia;

import java.util.List;
import java.util.Optional;

public class PaisRepository implements RepositoryGeografia<Pais, Long> {

    @Override
    public void guardar(Pais pais) {
        try (var  session = HibernateUtil.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();;
            session.persist(pais);
            transaction.commit();
        }
    }

    @Override
    public void actualitzar(Pais pais) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(pais);
            tx.commit();
        }
    }

    @Override
    public Optional<Pais> buscarPerId(Long id) {
        try  (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Pais.class, id));
        }
    }

    @Override
    public List<Pais> buscarTots() {
        try(var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pais", Pais.class).list();
        }
    }

    public Optional<Pais> buscarPerCodiIso(String codiIso) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Pais WHERE codiIso = :codiIso", Pais.class)
                    .setParameter("codiIso", codiIso)
                    .uniqueResultOptional();
        }
    }
}
