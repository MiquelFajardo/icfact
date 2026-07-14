package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecalcularFacturaServiceTest extends BaseRepositoryTest {

    private final FacturaRepository repository = new FacturaRepository();
    private final RecalcularFacturaService service = new RecalcularFacturaService();

    @Test
    void recalcularFactura() {

        Factura factura = repository.buscarPerNumero("F2026000001")
                .orElseThrow();

        service.executar(factura);

        assertEquals(0, factura.getSubtotal().compareTo(new BigDecimal("124.00")));
        assertEquals(0, factura.getIva().compareTo(new BigDecimal("26.04")));
        assertEquals(0, factura.getTotal().compareTo(new BigDecimal("150.04")));
    }
}