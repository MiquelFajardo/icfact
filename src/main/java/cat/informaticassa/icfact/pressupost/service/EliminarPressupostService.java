package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.LiniaPressupostRepository;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

public class EliminarPressupostService {

    private final PressupostRepository repository = new PressupostRepository();
    private final LiniaPressupostRepository liniaRepository = new LiniaPressupostRepository();

    public void executar(Long id) {

        Pressupost pressupost = repository.buscarPerIdAmbLinies(id)
                .orElseThrow(() ->
                        new PressupostNoExisteixException("El pressupost no existeix."));

        for (LiniaPressupost linia : pressupost.getLinies()) {
            liniaRepository.desactivar(linia);
        }

        repository.desactivar(pressupost);
    }
}