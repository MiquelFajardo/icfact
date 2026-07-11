package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class PressupostTestData {

    private static final PressupostRepository repository = new PressupostRepository();
    private static final ClientRepository clientRepository = new ClientRepository();

    private PressupostTestData() {
    }

    public static void carregar() {
        Client client1 = clientRepository.buscarPerNif("12345678A").orElseThrow();
        Client client2 = clientRepository.buscarPerNif("B12345678").orElseThrow();
        guardar("P-2026-000001", client1);
        guardar("P-2026-000002", client2);

    }

    private static void guardar(String numero, Client client) {
        Pressupost pressupost = Pressupost.builder()
                .numero(numero)
                .client(client)
                .data(LocalDate.now())
                .estat(EstatPressupost.ESBORRANY)
                .subtotal(BigDecimal.ZERO)
                .iva(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();
        repository.guardar(pressupost);
    }
}