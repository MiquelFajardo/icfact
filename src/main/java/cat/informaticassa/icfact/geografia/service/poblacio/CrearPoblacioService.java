package cat.informaticassa.icfact.geografia.service.poblacio;

import cat.informaticassa.icfact.geografia.exception.PoblacioJaExisteixException;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.repository.PoblacioRepository;
import cat.informaticassa.icfact.geografia.service.validar.ValidarPoblacio;

public class CrearPoblacioService {

    private final PoblacioRepository repository = new PoblacioRepository();
    private final ValidarPoblacio validar = new ValidarPoblacio();

    public void executar(Poblacio poblacio) {
        validar.executar(poblacio);
        repository.buscarPerNom(poblacio.getProvincia(), poblacio.getNom())
                .ifPresent(p -> {
                    throw new PoblacioJaExisteixException("Ja existeix una població amb aquest nom.");});
        repository.guardar(poblacio);
    }
}