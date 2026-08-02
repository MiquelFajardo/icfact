package cat.informaticassa.icfact.geografia.service.provincia;

import cat.informaticassa.icfact.geografia.exception.ProvinciaJaExisteixException;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.repository.ProvinciaRepository;
import cat.informaticassa.icfact.geografia.service.validar.ValidarProvincia;

public class ModificarProvinciaService {

    private final ProvinciaRepository repository = new ProvinciaRepository();
    private final ValidarProvincia validar = new ValidarProvincia();

    public void executar(Provincia provincia) {
        validar.executar(provincia);
        repository.buscarPerNom(provincia.getPais(), provincia.getNom())
                .filter(p -> !p.getId().equals(provincia.getId()))
                .ifPresent(p -> {
                    throw new ProvinciaJaExisteixException("Ja existeix una província amb aquest nom.");});
        repository.actualitzar(provincia);
    }
}