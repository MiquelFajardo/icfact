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
            return Optional.ofNullable(session.find(Pressupost.class, id));
        }
    }

    @Override
    public List<Pressupost> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pressupost ORDER BY data DESC",
                    Pressupost.class
            ).list();
        }
    }

    public List<Pressupost> buscarTotsActius() {

        try (var session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Pressupost WHERE actiu = true ORDER BY data DESC",
                    Pressupost.class
            ).list();

        }
    }

    public Optional<Pressupost> buscarPerNumero(String numero) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Pressupost WHERE numero = :numero",
                            Pressupost.class)
                    .setParameter("numero", numero)
                    .uniqueResultOptional();
        }
    }

    public List<Pressupost> buscarPerData(LocalDate data) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Pressupost WHERE data = :data",
                            Pressupost.class)
                    .setParameter("data", data)
                    .list();
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
}
