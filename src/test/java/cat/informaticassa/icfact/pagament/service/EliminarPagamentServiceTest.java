package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EliminarPagamentServiceTest extends BaseRepositoryTest {

    private final EliminarPagamentService service = new EliminarPagamentService();
    private final PagamentRepository repository = new PagamentRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    @Test
    void eliminarPagament() {

        Pagament pagament = repository.buscarTots().getFirst();

        service.executar(pagament);

        assertTrue(repository.buscarPerId(pagament.getId()).isEmpty());

        var factura = facturaRepository.buscarPerId(pagament.getFactura().getId()).orElseThrow();

        assertEquals(EstatFactura.EMESA, factura.getEstat());
    }
}