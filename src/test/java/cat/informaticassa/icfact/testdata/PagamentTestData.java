package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class PagamentTestData {

    private static final PagamentRepository repository = new PagamentRepository();
    private static final FacturaRepository facturaRepository = new FacturaRepository();
    private static final FormaPagamentRepository formaPagamentRepository = new FormaPagamentRepository();

    private PagamentTestData() {
    }

    public static void carregar() {

        Factura factura = facturaRepository.buscarPerNumero("F2026000001").orElseThrow();
        FormaPagament transferencia = formaPagamentRepository.buscarPerNom("Transferència").orElseThrow();

        guardar(
                factura,
                LocalDate.now(),
                new BigDecimal("250.00"),
                transferencia,
                "TRX001",
                "Primer pagament"
        );
    }

    private static void guardar(Factura factura, LocalDate dataPagament, BigDecimal importPagat, FormaPagament formaPagament,
                                String referencia, String observacions) {

        Pagament pagament = Pagament.builder()
                .factura(factura)
                .dataPagament(dataPagament)
                .importPagat(importPagat)
                .formaPagament(formaPagament)
                .referencia(referencia)
                .observacions(observacions)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();

        repository.guardar(pagament);
    }
}