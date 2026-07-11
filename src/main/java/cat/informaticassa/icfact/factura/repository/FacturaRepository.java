package cat.informaticassa.icfact.factura.repository;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FacturaRepository implements Repository<Factura, Long> {

    @Override
    public void guardar(Factura factura) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(factura);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Factura factura) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(factura);
            tx.commit();
        }
    }

    @Override
    public Optional<Factura> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Factura.class, id));
        }
    }

    @Override
    public List<Factura> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Factura ORDER BY data DESC",
                    Factura.class
            ).list();
        }
    }

    public List<Factura> buscarTotsActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Factura WHERE actiu = true ORDER BY data DESC",
                    Factura.class
            ).list();
        }
    }

    @Override
    public void eliminar(Factura factura) {
        factura.setActiu(false);
        actualitzar(factura);
    }

    @Override
    public void activar(Factura factura) {
        factura.setActiu(true);
        actualitzar(factura);
    }

    public Optional<Factura> buscarPerNumero(String numero) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Factura WHERE numero = :numero",
                            Factura.class)
                    .setParameter("numero", numero)
                    .uniqueResultOptional();
        }
    }

    public List<Factura> buscarPerData(LocalDate data) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Factura WHERE data = :data",
                            Factura.class)
                    .setParameter("data", data)
                    .list();
        }
    }
}