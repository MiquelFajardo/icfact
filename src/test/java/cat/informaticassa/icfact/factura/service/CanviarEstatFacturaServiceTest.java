package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CanviarEstatFacturaServiceTest extends BaseRepositoryTest {

    private final CanviarEstatFacturaService service = new CanviarEstatFacturaService();
    private final FacturaRepository repository = new FacturaRepository();

    @Test
    void canviarEstatFactura() {

        Factura factura = repository.buscarPerNumero("F2026000001")
                .orElseThrow();

        service.executar(factura.getId(), EstatFactura.EMESA);

        Factura actualitzada = repository.buscarPerId(factura.getId())
                .orElseThrow();

        assertEquals(EstatFactura.EMESA, actualitzada.getEstat());
    }
}