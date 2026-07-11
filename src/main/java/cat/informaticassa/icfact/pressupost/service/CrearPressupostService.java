package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CrearPressupostService {
    private final PressupostRepository repository = new PressupostRepository();

    public Pressupost crear(String numero, Client client) {

        Pressupost pressupost = Pressupost.builder()
                .numero(numero)
                .client(client)
                .data(LocalDate.now())
                .estat(EstatPressupost.ESBORRANY)
                .subtotal(BigDecimal.ZERO)
                .iva(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();

        repository.guardar(pressupost);

        return pressupost;
    }
}
