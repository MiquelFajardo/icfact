package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.LiniaPressupostRepository;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

public class ActivarPressupostService {

    private final PressupostRepository repository = new PressupostRepository();
    private final LiniaPressupostRepository liniaRepository = new LiniaPressupostRepository();

    public void executar(Long id) {

        Pressupost pressupost = repository.buscarPerIdIncloentInactius(id)
                .orElseThrow(() ->
                        new PressupostNoExisteixException("El pressupost no existeix."));

        repository.activar(pressupost);

        for (LiniaPressupost linia : pressupost.getLinies()) {
            liniaRepository.activar(linia);
        }
    }
}