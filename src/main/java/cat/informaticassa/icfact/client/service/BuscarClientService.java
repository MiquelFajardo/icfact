package cat.informaticassa.icfact.client.service;

import cat.informaticassa.icfact.client.exception.ClientNoExisteixException;
import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;

public class BuscarClientService {

    private final ClientRepository repository = new ClientRepository();

    public Client buscarPerId(Long id) {
        return repository.buscarPerId(id)
                .orElseThrow(() ->
                        new ClientNoExisteixException("El client no existeix."));
    }

    public Client buscarPerNif(String nif) {
        return repository.buscarPerNif(nif)
                .orElseThrow(() ->
                        new ClientNoExisteixException("El client no existeix."));
    }
}