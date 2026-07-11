package cat.informaticassa.icfact.iva.service;

import cat.informaticassa.icfact.iva.exception.IvaNoExisteixException;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;

import java.math.BigDecimal;

public class BuscarIvaService {
    private final IvaRepository repository = new IvaRepository();

    public Iva buscarPerId(Long id) {
        return repository.buscarPerId(id)
                .orElseThrow(() ->
                        new IvaNoExisteixException("L'IVA no existeix."));
    }

    public Iva buscarPerPercentatge(BigDecimal percentatge) {
        return repository.buscarPerPercentatge(percentatge)
                .orElseThrow(() ->
                        new IvaNoExisteixException("L'IVA no existeix."));
    }
}