package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.time.LocalDateTime;

public class CanviarEstatPressupostService {

    private final PressupostRepository repository = new PressupostRepository();
    public void executar(Long id, EstatPressupost estat) {
        Pressupost pressupost = repository.buscarPerIdIncloentInactius(id)
                .orElseThrow(() -> new PressupostNoExisteixException("El pressupost no existeix."));
        pressupost.setEstat(estat);
        pressupost.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(pressupost);
    }
}