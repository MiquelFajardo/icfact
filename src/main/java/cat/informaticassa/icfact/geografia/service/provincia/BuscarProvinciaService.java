package cat.informaticassa.icfact.geografia.service.provincia;

import cat.informaticassa.icfact.geografia.exception.ProvinciaNoExisteixException;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.repository.ProvinciaRepository;

public class BuscarProvinciaService {

    private final ProvinciaRepository repository = new ProvinciaRepository();

    public Provincia buscarPerId(Long id) {
        return repository.buscarPerId(id)
                .orElseThrow(() -> new ProvinciaNoExisteixException("La província no existeix."));
    }

    public Provincia buscarPerNom(String nom) {
        return repository.buscarPerNom(nom)
                .orElseThrow(() -> new ProvinciaNoExisteixException("La província no existeix."));
    }
}