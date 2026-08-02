package cat.informaticassa.icfact.client.service;

import cat.informaticassa.icfact.client.exception.ClientNoExisteixException;
import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;

import java.time.LocalDateTime;

public class ActivarClientService {

    private final ClientRepository repository = new ClientRepository();

    public void executar(Long id) {
        Client client = repository.buscarPerId(id).orElseThrow(() -> new ClientNoExisteixException("El client no existeix."));
        client.setActiu(true);
        client.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(client);
    }
}