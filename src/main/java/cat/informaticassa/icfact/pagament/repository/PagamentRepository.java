package cat.informaticassa.icfact.pagament.repository;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;
import cat.informaticassa.icfact.pagament.model.Pagament;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PagamentRepository implements Repository<Pagament, Long> {

    @Override
    public void guardar(Pagament pagament) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(pagament);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Pagament pagament) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(pagament);
            tx.commit();
        }
    }

    @Override
    public Optional<Pagament> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pagament
                    WHERE id = :id
                    AND actiu = true
                    """, Pagament.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Pagament> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pagament
                    WHERE actiu = true
                    ORDER BY dataPagament DESC
                    """, Pagament.class)
                    .list();
        }
    }

    public List<Pagament> buscarActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pagament
                    WHERE actiu = true
                    ORDER BY dataPagament DESC
                    """, Pagament.class)
                    .list();
        }
    }

    public List<Pagament> buscarInactius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pagament
                    WHERE actiu = false
                    ORDER BY dataPagament DESC
                    """, Pagament.class)
                    .list();
        }
    }

    public List<Pagament> buscarPerFactura(Factura factura) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Pagament
                    WHERE factura = :factura
                    AND actiu = true
                    ORDER BY dataPagament ASC
                    """, Pagament.class)
                    .setParameter("factura", factura)
                    .list();
        }
    }

    public BigDecimal calcularImportPagat(Factura factura) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {

            BigDecimal total = session.createQuery("""
                    SELECT SUM(importPagat)
                    FROM Pagament
                    WHERE factura = :factura
                    AND actiu = true
                    """, BigDecimal.class)
                    .setParameter("factura", factura)
                    .uniqueResult();

            return total == null ? BigDecimal.ZERO : total;
        }
    }

    public Optional<Pagament> buscarPerIdIncloentInactius(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                FROM Pagament
                WHERE id = :id
                """, Pagament.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public List<Pagament> buscarPerFacturaIncloentInactius(Factura factura) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                FROM Pagament
                WHERE factura = :factura
                ORDER BY dataPagament ASC
                """, Pagament.class)
                    .setParameter("factura", factura)
                    .list();
        }
    }

    @Override
    public void eliminar(Pagament pagament) {
        pagament.setActiu(false);
        actualitzar(pagament);
    }

    @Override
    public void activar(Pagament pagament) {
        pagament.setActiu(true);
        actualitzar(pagament);
    }
}