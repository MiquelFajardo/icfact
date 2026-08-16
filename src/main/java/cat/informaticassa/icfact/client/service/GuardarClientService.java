package cat.informaticassa.icfact.client.service;

import cat.informaticassa.icfact.client.exception.ClientJaExisteixException;
import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.client.service.validar.ValidarClient;

import java.time.LocalDateTime;

public class GuardarClientService {
    private final ClientRepository repository = new ClientRepository();
    private final ValidarClient validarClient = new ValidarClient();

    public void executar(Client client) {
        validarClient.executar(client);
        if (repository.buscarPerNif(client.getNif()).isPresent()) {
            throw new ClientJaExisteixException("Ja existeix un client amb aquest NIF.");
        }
        client.setActiu(true);
        client.setDataCreacio(LocalDateTime.now());
        client.setDataModificacio(LocalDateTime.now());
        repository.guardar(client);
    }
}