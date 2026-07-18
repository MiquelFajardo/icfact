package cat.informaticassa.icfact.client.service;

import cat.informaticassa.icfact.client.exception.ClientJaExisteixException;
import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;

import java.time.LocalDateTime;

public class GuardarClientService {

    private final ClientRepository repository = new ClientRepository();

    public void executar(Client client) {
        if (repository.buscarPerNif(client.getNif()).isPresent()) {
            throw new ClientJaExisteixException("Ja existeix un client amb aquest NIF.");
        }

        client.setActiu(true);
        client.setDataCreacio(LocalDateTime.now());
        client.setDataModificacio(LocalDateTime.now());
        repository.guardar(client);
    }
}
