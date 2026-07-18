package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.factura.service.RecalcularFacturaService;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class FacturaTestData {

    private static final FacturaRepository repository = new FacturaRepository();
    private static final ClientRepository clientRepository = new ClientRepository();
    private static final ProducteRepository producteRepository = new ProducteRepository();
    private static final IvaRepository ivaRepository = new IvaRepository();
    private static final RecalcularFacturaService recalcularService = new RecalcularFacturaService();
    private static final FormaPagamentRepository formaPagamentRepository = new FormaPagamentRepository();

    private FacturaTestData() {
    }

    public static void carregar() {

        Client client1 = clientRepository.buscarPerNif("12345678A").orElseThrow();
        Client client2 = clientRepository.buscarPerNif("B12345678").orElseThrow();

        FormaPagament transferencia = formaPagamentRepository.buscarPerNom("Transferència").orElseThrow();

        Producte manteniment = producteRepository.buscarPerCodi("P0001").orElseThrow();
        Producte windows = producteRepository.buscarPerCodi("P0002").orElseThrow();

        Iva iva21 = ivaRepository.buscarPerPercentatge(new BigDecimal("21.00")).orElseThrow();

        Factura factura1 = Factura.builder()
                .numero("F2026000001")
                .client(client1)
                .data(LocalDate.now())
                .estat(EstatFactura.ESBORRANY)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .formaPagament(transferencia)
                .observacions("Pagament a 30 dies")
                .build();

        factura1.getLinies().add(
                LiniaFactura.builder()
                        .factura(factura1)
                        .producte(manteniment)
                        .descripcio(manteniment.getNom())
                        .quantitat(new BigDecimal("2"))
                        .preu(new BigDecimal("35.00"))
                        .dte(BigDecimal.ZERO)
                        .iva(iva21)
                        .actiu(true)
                        .build());

        factura1.getLinies().add(
                LiniaFactura.builder()
                        .factura(factura1)
                        .producte(windows)
                        .descripcio(windows.getNom())
                        .quantitat(BigDecimal.ONE)
                        .preu(new BigDecimal("60.00"))
                        .dte(new BigDecimal("10.00"))
                        .iva(iva21)
                        .actiu(true)
                        .build());

        recalcularService.executar(factura1);
        repository.guardar(factura1);

        Factura factura2 = Factura.builder()
                .numero("F2026000002")
                .client(client2)
                .data(LocalDate.now())
                .estat(EstatFactura.ESBORRANY)
                .subtotal(BigDecimal.ZERO)
                .iva(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .formaPagament(transferencia)
                .observacions("Pagament a 60 dies")
                .build();

        recalcularService.executar(factura2);
        repository.guardar(factura2);
    }
}