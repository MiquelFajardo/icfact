package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicarFacturaServiceTest extends BaseRepositoryTest {

    private final DuplicarFacturaService service = new DuplicarFacturaService();
    private final FacturaRepository repository = new FacturaRepository();

    @Test
    void duplicarFactura() {

        Factura original = repository.buscarPerNumero("F2026000001")
                .orElseThrow();

        Factura copia = service.executar(original.getId());

        assertNotEquals(original.getId(), copia.getId());
        assertNotEquals(original.getNumero(), copia.getNumero());

        assertEquals(original.getClient().getId(), copia.getClient().getId());
        assertEquals(EstatFactura.ESBORRANY, copia.getEstat());

        assertEquals(original.getLinies().size(), copia.getLinies().size());

        assertEquals(0, original.getSubtotal().compareTo(copia.getSubtotal()));
        assertEquals(0, original.getIva().compareTo(copia.getIva()));
        assertEquals(0, original.getTotal().compareTo(copia.getTotal()));
    }
}