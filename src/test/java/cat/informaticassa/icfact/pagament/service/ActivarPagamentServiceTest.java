package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivarPagamentServiceTest extends BaseRepositoryTest {

    private final ActivarPagamentService service = new ActivarPagamentService();
    private final PagamentRepository repository = new PagamentRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    @Test
    void activarPagament() {
        Pagament pagament = repository.buscarTots().getFirst();
        repository.desactivar(pagament);
        service.executar(pagament);
        assertTrue(repository.buscarPerId(pagament.getId()).isPresent());
        var factura = facturaRepository.buscarPerId(pagament.getFactura().getId()).orElseThrow();
        assertEquals(EstatFactura.COBRADA, factura.getEstat());
    }
}