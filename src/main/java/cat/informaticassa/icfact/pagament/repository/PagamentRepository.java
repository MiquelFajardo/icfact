package cat.informaticassa.icfact.pagament.repository;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pressupost.model.Pressupost;

import java.math.BigDecimal;
import java.util.List;

public class PagamentRepository
        extends AbstractActivableRepository<Pagament, Long> {

    @Override
    protected String ordrePerDefecte() {
        return "dataPagament DESC";
    }

    public List<Pagament> buscarPerPressupost(Pressupost pressupost) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("""
                    FROM Pagament
                    WHERE actiu = true
                    AND (
                        pressupost = :pressupost
                        OR factura IN (
                            SELECT f
                            FROM Factura f
                            WHERE f.pressupost = :pressupost
                        )
                    )
                    ORDER BY dataPagament ASC
                    """, Pagament.class)
                    .setParameter("pressupost", pressupost)
                    .list();
        }
    }

    public BigDecimal calcularImportPagat(Pressupost pressupost) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {

            BigDecimal total = session.createQuery("""
                    SELECT COALESCE(SUM(p.importPagat), 0)
                    FROM Pagament p
                    WHERE p.actiu = true
                    AND (
                        p.pressupost = :pressupost
                        OR p.factura IN (
                            SELECT f
                            FROM Factura f
                            WHERE f.pressupost = :pressupost
                        )
                    )
                    """, BigDecimal.class)
                    .setParameter("pressupost", pressupost)
                    .uniqueResult();

            return total == null
                    ? BigDecimal.ZERO
                    : total;
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
                SELECT COALESCE(SUM(p.importPagat), 0)
                FROM Pagament p
                WHERE p.actiu = true
                AND (
                    p.factura = :factura
                    OR (
                        p.pressupost = :pressupost
                        AND :pressupost IS NOT NULL
                    )
                )
                """, BigDecimal.class)
                    .setParameter("factura", factura)
                    .setParameter(
                            "pressupost",
                            factura.getPressupost()
                    )
                    .uniqueResult();

            return total == null
                    ? BigDecimal.ZERO
                    : total;
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

    public void associarPagamentsAFactura(Pressupost pressupost, Factura factura) {
        if (pressupost == null || factura == null || factura.getId() == null) {
            throw new IllegalArgumentException("El pressupost i la factura han de ser vàlids i la factura ha d'estar guardada.");        }
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            int actualitzats = session.createMutationQuery("""
                    UPDATE Pagament p
                    SET p.factura = :factura
                    WHERE p.pressupost = :pressupost
                    AND p.actiu = true
                    """)
                    .setParameter("factura", factura)
                    .setParameter("pressupost", pressupost)
                    .executeUpdate();
            transaction.commit();
        }
    }
}