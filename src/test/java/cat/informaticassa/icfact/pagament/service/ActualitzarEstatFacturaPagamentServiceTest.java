package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActualitzarEstatFacturaPagamentServiceTest extends BaseRepositoryTest {

    private final ActualitzarEstatFacturaPagamentService service =
            new ActualitzarEstatFacturaPagamentService();

    private final FacturaRepository facturaRepository =
            new FacturaRepository();

    private final PagamentRepository pagamentRepository =
            new PagamentRepository();

    @Test
    void facturaCobrada() {

        Factura factura = facturaRepository.buscarPerNumero("F2026000001").orElseThrow();

        service.executar(factura);

        factura = facturaRepository.buscarPerId(factura.getId()).orElseThrow();

        assertEquals(EstatFactura.COBRADA, factura.getEstat());
    }

    @Test
    void facturaEmesa() {

        Factura factura = facturaRepository.buscarPerNumero("F2026000001").orElseThrow();

        Pagament pagament = pagamentRepository.buscarPerFactura(factura).getFirst();

        pagamentRepository.eliminar(pagament);

        service.executar(factura);

        factura = facturaRepository.buscarPerId(factura.getId()).orElseThrow();

        assertEquals(EstatFactura.EMESA, factura.getEstat());
    }
}