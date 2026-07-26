package cat.informaticassa.icfact.producte.service;

import cat.informaticassa.icfact.producte.exception.ProducteNoExisteixException;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;

import java.util.List;

public class BuscarProducteService {

    private final ProducteRepository repository = new ProducteRepository();

    public Producte buscarPerId(Long id) {
        return repository.buscarPerId(id)
                .orElseThrow(() ->
                        new ProducteNoExisteixException("El producte no existeix."));
    }

    public Producte buscarPerCodi(String codi) {
        return repository.buscarPerCodi(codi)
                .orElseThrow(() ->
                        new ProducteNoExisteixException("El producte no existeix."));
    }

    public List<Producte> buscarTots() {
        return repository.buscarTots();
    }

    public List<Producte> buscarActius() {
        return repository.buscarTots();
    }

    public List<Producte> buscarInactius() {
        return repository.buscarInactius();
    }
}