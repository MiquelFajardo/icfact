package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliminarFacturaServiceTest extends BaseRepositoryTest {

    private final EliminarFacturaService service = new EliminarFacturaService();
    private final FacturaRepository repository = new FacturaRepository();

    @Test
    void eliminarFactura() {

        Factura factura = repository.buscarPerNumero("F2026000001")
                .orElseThrow();

        service.executar(factura.getId());

        assertTrue(repository.buscarPerId(factura.getId()).isEmpty());

        Factura eliminada = repository.buscarPerIdIncloentInactius(factura.getId())
                .orElseThrow();

        assertFalse(eliminada.getActiu());
    }
}