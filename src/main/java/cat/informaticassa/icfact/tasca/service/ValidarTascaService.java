package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.tasca.exception.TascaException;
import cat.informaticassa.icfact.tasca.model.Tasca;

public class ValidarTascaService {

    public void executar(Tasca tasca) {

        if (tasca == null) {
            throw new TascaException("La tasca no pot ser nul·la.");
        }

        if (tasca.getTitol() == null || tasca.getTitol().isBlank()) {
            throw new TascaException("El títol de la tasca és obligatori.");
        }

        if (tasca.getTitol().length() > 150) {
            throw new TascaException("El títol no pot superar els 150 caràcters.");
        }
    }
}