package cat.informaticassa.icfact.geografia.service.poblacio;

import cat.informaticassa.icfact.geografia.exception.PoblacioNoExisteixException;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.repository.PoblacioRepository;

public class BuscarPoblacioService {

    private final PoblacioRepository repository = new PoblacioRepository();

    public Poblacio buscarPerId(Long id) {
        return repository.buscarPerId(id)
                .orElseThrow(() -> new PoblacioNoExisteixException("La població no existeix."));
    }

    public Poblacio buscarPerNom(Provincia provincia, String nom) {
        return repository.buscarPerNom(provincia, nom)
                .orElseThrow(() -> new PoblacioNoExisteixException("La població no existeix."));
    }
}