package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.pressupost.exception.PressupostSenseClientException;
import cat.informaticassa.icfact.pressupost.exception.PressupostSenseDataException;
import cat.informaticassa.icfact.pressupost.exception.PressupostSenseLiniesException;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;

import java.math.BigDecimal;

public class ValidarPressupostService {

    public void executar(Pressupost pressupost) {
        if (pressupost == null) throw new IllegalArgumentException("El pressupost no pot ser nul.");
        validarClient(pressupost);
        validarLinies(pressupost);
        validarImports(pressupost);
        validarDates(pressupost);
    }

    private void validarClient(Pressupost pressupost) {
        Client client = pressupost.getClient();
        if (client == null || client.getId() == null) throw new PressupostSenseClientException("El pressupost ha de tenir un client.");
        if (!client.isActiu()) throw new IllegalArgumentException("El client està inactiu.");
    }

    private void validarLinies(Pressupost pressupost) {
        if (pressupost.getLinies() == null || pressupost.getLinies().isEmpty()) throw new PressupostSenseLiniesException("El pressupost ha de tenir almenys una línia.");

        for (LiniaPressupost linia : pressupost.getLinies()) {
            if (linia.getDescripcio() == null || linia.getDescripcio().isBlank()) throw new IllegalArgumentException("La descripció de la línia és obligatòria.");
            if (linia.getQuantitat() == null || linia.getQuantitat().compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("La quantitat ha de ser superior a zero.");
            if (linia.getPreu() == null) throw new IllegalArgumentException("El preu és obligatori.");
            if (linia.getDte() == null || linia.getDte().compareTo(BigDecimal.ZERO) < 0 || linia.getDte().compareTo(BigDecimal.valueOf(100)) > 0) throw new IllegalArgumentException("El descompte ha d'estar entre 0 i 100.");
            if (linia.getIva() == null) throw new IllegalArgumentException("La línia ha de tenir un IVA.");
        }
    }

    private void validarImports(Pressupost pressupost) {
        if (pressupost.getSubtotal() != null && pressupost.getSubtotal().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("El subtotal no pot ser negatiu.");
        if (pressupost.getIva() != null && pressupost.getIva().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("L'IVA no pot ser negatiu.");
        if (pressupost.getTotal() != null && pressupost.getTotal().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("El total no pot ser negatiu.");
    }

    private void validarDates(Pressupost pressupost) {
        if (pressupost.getData() == null) throw new PressupostSenseDataException("El pressupost ha de tenir una data.");
    }
}