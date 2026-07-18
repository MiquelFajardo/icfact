package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

public class BuscarPressupostService {

    private final PressupostRepository repository = new PressupostRepository();

    public Pressupost executar(Long id) {
        return repository.buscarPerId(id)
                .orElseThrow(() ->
                        new PressupostNoExisteixException("El pressupost no existeix."));
    }
}
