package cat.informaticassa.icfact.geografia.service.provincia;

import cat.informaticassa.icfact.geografia.exception.ProvinciaJaExisteixException;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.repository.ProvinciaRepository;
import cat.informaticassa.icfact.geografia.service.validar.ValidarProvincia;

public class CrearProvinciaService {

    private final ProvinciaRepository repository = new ProvinciaRepository();
    private final ValidarProvincia validarProvincia = new ValidarProvincia();

    public void executar(Provincia provincia) {
        validarProvincia.executar(provincia);
        repository.buscarPerNom(provincia.getPais(), provincia.getNom()                )
                .ifPresent(p -> {
                    throw new ProvinciaJaExisteixException("Ja existeix una província amb aquest nom.");});
        repository.guardar(provincia);
    }
}