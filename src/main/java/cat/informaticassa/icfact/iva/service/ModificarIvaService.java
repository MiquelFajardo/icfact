package cat.informaticassa.icfact.iva.service;

import cat.informaticassa.icfact.iva.exception.IvaNoExisteixException;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;

import java.time.LocalDateTime;

public class ModificarIvaService {

    private final IvaRepository repository = new IvaRepository();

    public void executar(Iva iva) {
        repository.buscarPerId(iva.getId())
                .orElseThrow(() ->
                        new IvaNoExisteixException("L'IVA no existeix."));

        iva.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(iva);
    }
}