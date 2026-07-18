package cat.informaticassa.icfact.producte.service;

import cat.informaticassa.icfact.producte.exception.ProducteNoExisteixException;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;

import java.time.LocalDateTime;

public class ModificarProducteService {

    private final ProducteRepository repository = new ProducteRepository();

    public void executar(Producte producte) {
        repository.buscarPerId(producte.getId())
                .orElseThrow(() ->
                        new ProducteNoExisteixException("El producte no existeix."));

        producte.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(producte);
    }
}