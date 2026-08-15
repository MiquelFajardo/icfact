package cat.informaticassa.icfact.client.service;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;

import java.util.List;

public class BuscarClientsService {
    private final ClientRepository repository = new ClientRepository();

    public List<Client> buscarTots() {
        return repository.buscarTots();
    }

    public List<Client> buscar(String text, boolean actius, boolean inactius) {
        return repository.buscar(text, actius, inactius);
    }
}