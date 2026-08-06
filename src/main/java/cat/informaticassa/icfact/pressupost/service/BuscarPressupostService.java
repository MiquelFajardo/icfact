package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.util.List;

public class BuscarPressupostService {
    private final PressupostRepository repository = new PressupostRepository();

    public Pressupost executar(Long id) {
        return repository.buscarPerId(id).orElseThrow(() -> new PressupostNoExisteixException("El pressupost no existeix."));
    }

    public List<Pressupost> buscarTots() {
        return repository.buscarTots();
    }

    public List<Pressupost> buscarActius() {
        return repository.buscarActius();
    }

    public List<Pressupost> buscarInactius() {
        return repository.buscarInactius();
    }

    public List<Pressupost> buscar(String text, boolean actius, boolean inactius, Client client) {
        return repository.buscar(text, actius, inactius, client);
    }
}