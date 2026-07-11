package cat.informaticassa.icfact.client.service;

import cat.informaticassa.icfact.client.exception.ClientNoExisteixException;
import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;

import java.time.LocalDateTime;

public class DesactivarClientService {

    private final ClientRepository repository = new ClientRepository();

    public void executar(Client client) {
        repository.buscarPerId(client.getId())
                .orElseThrow(() ->
                        new ClientNoExisteixException("El client no existeix."));

        client.setDataModificacio(LocalDateTime.now());
        repository.eliminar(client);
    }
}