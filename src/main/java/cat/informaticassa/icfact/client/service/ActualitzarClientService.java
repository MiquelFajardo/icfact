package cat.informaticassa.icfact.client.service;

import cat.informaticassa.icfact.client.exception.ClientJaExisteixException;
import cat.informaticassa.icfact.client.exception.ClientNoExisteixException;
import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.client.service.validar.ValidarClient;

import java.time.LocalDateTime;

public class ActualitzarClientService {

    private final ClientRepository repository = new ClientRepository();
    private final ValidarClient validarClient = new ValidarClient();

    public void executar(Client client) {
        validarClient.executar(client);
        Client clientActual = repository.buscarPerIdIncloentInactius(client.getId()).orElseThrow(() -> new ClientNoExisteixException("El client no existeix."));
        repository.buscarPerNif(client.getNif()).ifPresent(existent -> {
            if (!existent.getId().equals(client.getId())) {
                throw new ClientJaExisteixException("Ja existeix un client amb aquest NIF.");
            }
        });
        clientActual.actualitzarDades(client);
        clientActual.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(clientActual);
    }
}