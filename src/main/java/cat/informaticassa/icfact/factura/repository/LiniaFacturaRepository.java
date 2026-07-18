package cat.informaticassa.icfact.factura.repository;

import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;

import java.util.List;
import java.util.Optional;

public class LiniaFacturaRepository implements Repository<LiniaFactura, Long> {

    @Override
    public void guardar(LiniaFactura linia) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(linia);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(LiniaFactura linia) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(linia);
            tx.commit();
        }
    }

    @Override
    public Optional<LiniaFactura> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM LiniaFactura
                    WHERE id = :id
                    AND actiu = true
                    """, LiniaFactura.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public Optional<LiniaFactura> buscarPerIdIncloentInactius(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM LiniaFactura
                    WHERE id = :id
                    """, LiniaFactura.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<LiniaFactura> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM LiniaFactura
                    WHERE actiu = true
                    ORDER BY id
                    """, LiniaFactura.class)
                    .list();
        }
    }

    public List<LiniaFactura> buscarActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM LiniaFactura
                    WHERE actiu = true
                    ORDER BY id
                    """, LiniaFactura.class)
                    .list();
        }
    }

    public List<LiniaFactura> buscarInactius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM LiniaFactura
                    WHERE actiu = false
                    ORDER BY id
                    """, LiniaFactura.class)
                    .list();
        }
    }

    @Override
    public void eliminar(LiniaFactura linia) {
        linia.setActiu(false);
        actualitzar(linia);
    }

    @Override
    public void activar(LiniaFactura linia) {
        linia.setActiu(true);
        actualitzar(linia);
    }

    public List<LiniaFactura> buscarPerFactura(Long facturaId) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM LiniaFactura
                    WHERE factura.id = :id
                    AND actiu = true
                    ORDER BY id
                    """, LiniaFactura.class)
                    .setParameter("id", facturaId)
                    .list();
        }
    }
}