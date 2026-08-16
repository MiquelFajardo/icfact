package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.exception.PressupostNoTrobatException;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.time.LocalDateTime;

public class ModificarPressupostService {
    private final PressupostRepository repository = new PressupostRepository();
    private final ValidarPressupostService validarService = new ValidarPressupostService();
    private final RecalcularPressupostService recalcularService = new RecalcularPressupostService();

    public void executar(Pressupost pressupost) {
        Pressupost existent = repository.buscarPerIdIncloentInactius(pressupost.getId()).orElseThrow(() ->
                new PressupostNoTrobatException("No s'ha trobat el pressupost."));
        existent.setClient(pressupost.getClient());
        existent.setData(pressupost.getData());
        existent.setEstat(pressupost.getEstat());
        existent.setActiu(pressupost.isActiu());
        existent.setFormaPagament(pressupost.getFormaPagament());
        existent.setObservacions(pressupost.getObservacions());
        existent.getLinies().clear();
        for (LiniaPressupost linia : pressupost.getLinies()) {
            linia.setPressupost(existent);
            existent.getLinies().add(linia);
        }
        validarService.executar(existent);
        recalcularService.executar(existent);
        existent.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(existent);
    }
}