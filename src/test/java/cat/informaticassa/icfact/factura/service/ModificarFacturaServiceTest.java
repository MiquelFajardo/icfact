package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModificarFacturaServiceTest extends BaseRepositoryTest {

    private final ModificarFacturaService service = new ModificarFacturaService();
    private final FacturaRepository repository = new FacturaRepository();

    @Test
    void modificarFactura() {

        Factura factura = repository.buscarPerNumero("F2026000001")
                .orElseThrow();

        factura.setObservacions("Factura modificada");

        LiniaFactura linia = factura.getLinies().get(0);
        linia.setQuantitat(new BigDecimal("3"));

        Factura resultat = service.executar(factura);

        assertEquals("Factura modificada", resultat.getObservacions());
        assertEquals(0,
                resultat.getLinies().get(0).getQuantitat().compareTo(new BigDecimal("3")));

        assertEquals(0,
                resultat.getSubtotal().compareTo(new BigDecimal("159.00")));

        assertEquals(0,
                resultat.getIva().compareTo(new BigDecimal("33.39")));

        assertEquals(0,
                resultat.getTotal().compareTo(new BigDecimal("192.39")));
    }
}