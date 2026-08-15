package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.util.List;

public class BuscarPressupostService {
    private final PressupostRepository repository = new PressupostRepository();

    public List<Pressupost> buscarActius() {
        return repository.buscarActius();
    }


    public List<Pressupost> buscar(String text, boolean actius, boolean inactius, Client client) {
        return repository.buscar(text, actius, inactius, client);
    }
}