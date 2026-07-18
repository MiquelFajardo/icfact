package cat.informaticassa.icfact.iva.service;

import cat.informaticassa.icfact.iva.exception.IvaJaExisteixException;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;

import java.time.LocalDateTime;

public class CrearIvaService {

    private final IvaRepository repository = new IvaRepository();

    public void executar(Iva iva) {
        if (repository.buscarPerPercentatge(iva.getPercentatge()).isPresent()) {
            throw new IvaJaExisteixException("Ja existeix un IVA amb aquest percentatge.");
        }

        iva.setActiu(true);
        iva.setDataCreacio(LocalDateTime.now());
        iva.setDataModificacio(LocalDateTime.now());
        repository.guardar(iva);
    }
}