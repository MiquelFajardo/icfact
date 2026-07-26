package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivarFacturaServiceTest extends BaseRepositoryTest {

    private final ActivarFacturaService activarService = new ActivarFacturaService();
    private final EliminarFacturaService eliminarService = new EliminarFacturaService();
    private final FacturaRepository repository = new FacturaRepository();

    @Test
    void activarFactura() {

        Factura factura = repository.buscarPerNumero("F2026000001")
                .orElseThrow();

        eliminarService.executar(factura.getId());

        activarService.executar(factura.getId());

        Factura activada = repository.buscarPerId(factura.getId())
                .orElseThrow();

        assertTrue(activada.isActiu());
    }
}