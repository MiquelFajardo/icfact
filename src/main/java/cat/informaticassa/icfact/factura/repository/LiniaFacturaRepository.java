package cat.informaticassa.icfact.factura.repository;

import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import java.util.List;

public class LiniaFacturaRepository extends AbstractActivableRepository<LiniaFactura, Long> {

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