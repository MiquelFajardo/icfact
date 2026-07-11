package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class FacturaTestData {

    private static final FacturaRepository repository = new FacturaRepository();
    private static final ClientRepository clientRepository = new ClientRepository();

    private FacturaTestData() {
    }

    public static void carregar() {
        Client client1 = clientRepository.buscarPerNif("12345678A").orElseThrow();
        Client client2 = clientRepository.buscarPerNif("B12345678").orElseThrow();
        guardar("F-2026-000001", client1);
        guardar("F-2026-000002", client2);
    }

    private static void guardar(String numero, Client client) {
        Factura factura = Factura.builder()
                .numero(numero)
                .client(client)
                .data(LocalDate.now())
                .estat(EstatFactura.ESBORRANY)
                .subtotal(BigDecimal.ZERO)
                .iva(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();
        repository.guardar(factura);
    }
}