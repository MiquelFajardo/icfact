package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.tasca.model.Tasca;

import java.time.LocalDate;

public class ValidarTascaService {

    public void executar(Tasca tasca) {

        if (tasca == null) {
            throw new IllegalArgumentException("La tasca no pot ser nul·la." );
        }

        if (tasca.getTitol() == null || tasca.getTitol().isBlank()) {
            throw new IllegalArgumentException("El títol de la tasca és obligatori.");
        }

        if (tasca.getDataLimit() != null && tasca.getDataLimit().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data límit no pot ser anterior a avui.");
        }
    }
}