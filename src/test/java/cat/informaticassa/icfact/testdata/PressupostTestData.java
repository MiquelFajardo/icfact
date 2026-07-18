package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import cat.informaticassa.icfact.pressupost.service.RecalcularPressupostService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class PressupostTestData {

    private static final PressupostRepository repository = new PressupostRepository();
    private static final ClientRepository clientRepository = new ClientRepository();
    private static final ProducteRepository producteRepository = new ProducteRepository();
    private static final IvaRepository ivaRepository = new IvaRepository();
    private static final RecalcularPressupostService recalcularService = new RecalcularPressupostService();
    private static final FormaPagamentRepository formaPagamentRepository = new FormaPagamentRepository();

    private PressupostTestData() {
    }

    public static void carregar() {

        Client client1 = clientRepository.buscarPerNif("12345678A").orElseThrow();
        Client client2 = clientRepository.buscarPerNif("B12345678").orElseThrow();

        FormaPagament transferencia = formaPagamentRepository.buscarPerNom("Transferència").orElseThrow();

        Producte manteniment = producteRepository.buscarPerCodi("P0001").orElseThrow();
        Producte windows = producteRepository.buscarPerCodi("P0002").orElseThrow();

        Iva iva21 = ivaRepository.buscarPerPercentatge(new BigDecimal("21.00")).orElseThrow();

        Pressupost pressupost1 = Pressupost.builder()
                .numero("P2026000001")
                .client(client1)
                .data(LocalDate.now())
                .estat(EstatPressupost.ESBORRANY)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .formaPagament(transferencia)
                .observacions("Validesa 30 dies")
                .build();

        pressupost1.getLinies().add(
                LiniaPressupost.builder()
                        .pressupost(pressupost1)
                        .producte(manteniment)
                        .descripcio(manteniment.getNom())
                        .quantitat(new BigDecimal("2"))
                        .preu(new BigDecimal("35.00"))
                        .dte(BigDecimal.ZERO)
                        .iva(iva21)
                        .actiu(true)
                        .build());

        pressupost1.getLinies().add(
                LiniaPressupost.builder()
                        .pressupost(pressupost1)
                        .producte(windows)
                        .descripcio(windows.getNom())
                        .quantitat(BigDecimal.ONE)
                        .preu(new BigDecimal("60.00"))
                        .dte(new BigDecimal("10.00"))
                        .iva(iva21)
                        .actiu(true)
                        .build());

        recalcularService.executar(pressupost1);
        repository.guardar(pressupost1);

        Pressupost pressupost2 = Pressupost.builder()
                .numero("P2026000002")
                .client(client2)
                .data(LocalDate.now())
                .estat(EstatPressupost.ESBORRANY)
                .subtotal(BigDecimal.ZERO)
                .iva(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .formaPagament(transferencia)
                .observacions("Validesa 30 dies")
                .build();

        recalcularService.executar(pressupost2);
        repository.guardar(pressupost2);
    }
}