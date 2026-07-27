package cat.informaticassa.icfact.geografia.service.pais;

import cat.informaticassa.icfact.geografia.exception.PaisNoExisteixException;
import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.repository.PaisRepository;

public class BuscarPaisService {

    private final PaisRepository repository = new PaisRepository();

    public Pais buscarPerId(Long id) {
        return repository.buscarPerId(id)
                .orElseThrow(() -> new PaisNoExisteixException("El país no existeix."));
    }

    public Pais buscarPerNom(String nom) {
        return repository.buscarPerNom(nom)
                .orElseThrow(() -> new PaisNoExisteixException("El país no existeix."));
    }
}