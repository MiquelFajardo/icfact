package cat.informaticassa.icfact.producte.service;

import cat.informaticassa.icfact.producte.exception.ProducteJaExisteixException;
import cat.informaticassa.icfact.producte.exception.ProducteNoExisteixException;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;
import cat.informaticassa.icfact.producte.service.validar.ValidarProducte;

import java.time.LocalDateTime;

public class ModificarProducteService {

    private final ProducteRepository repository = new ProducteRepository();
    private final ValidarProducte validarService = new ValidarProducte();

    public void executar(Producte producte) {
        validarService.executar(producte);
        Producte existent = repository.buscarPerId(producte.getId()).orElseThrow(() ->
                new ProducteNoExisteixException("El producte no existeix."));

        repository.buscarPerCodi(producte.getCodi()).ifPresent(altre -> {
            if (!altre.getId().equals(existent.getId())) {
                throw new ProducteJaExisteixException("Ja existeix un producte amb aquest codi.");
            }
        });
        producte.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(producte);
    }
}