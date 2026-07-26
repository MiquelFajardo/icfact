package cat.informaticassa.icfact.pagament.repository;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PagamentRepository extends AbstractActivableRepository<Pagament, Long> {

    @Override
    protected String ordrePerDefecte() {
        return "dataPagament DESC";
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
}