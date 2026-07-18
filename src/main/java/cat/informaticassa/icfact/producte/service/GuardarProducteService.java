package cat.informaticassa.icfact.producte.service;

import cat.informaticassa.icfact.producte.exception.ProducteJaExisteixException;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;

import java.time.LocalDateTime;

public class GuardarProducteService {

    private final ProducteRepository repository = new ProducteRepository();

    public void executar(Producte producte) {
        if (repository.buscarPerCodi(producte.getCodi()).isPresent()) {
            throw new ProducteJaExisteixException("Ja existeix un producte amb aquest codi.");
        }

        producte.setActiu(true);
        producte.setDataCreacio(LocalDateTime.now());
        producte.setDataModificacio(LocalDateTime.now());
        repository.guardar(producte);
    }
}