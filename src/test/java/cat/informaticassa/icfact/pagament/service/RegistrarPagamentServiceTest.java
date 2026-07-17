package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrarPagamentServiceTest extends BaseRepositoryTest {

    private final RegistrarPagamentService service = new RegistrarPagamentService();
    private final FacturaRepository facturaRepository = new FacturaRepository();
    private final FormaPagamentRepository formaPagamentRepository = new FormaPagamentRepository();

    @Test
    void registrarPagament() {

        var factura = facturaRepository.buscarPerNumero("F2026000002").orElseThrow();

        var forma = formaPagamentRepository.buscarPerNom("Transferència").orElseThrow();

        Pagament pagament = Pagament.builder()
                .factura(factura)
                .dataPagament(LocalDate.now())
                .importPagat(BigDecimal.TEN)
                .formaPagament(forma)
                .referencia("TRX002")
                .observacions("Test")
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();

        service.executar(pagament);

        factura = facturaRepository.buscarPerId(factura.getId()).orElseThrow();

        assertEquals(EstatFactura.COBRADA, factura.getEstat());
    }
}