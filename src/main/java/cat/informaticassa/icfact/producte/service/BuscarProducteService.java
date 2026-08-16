package cat.informaticassa.icfact.producte.service;

import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;
import java.util.List;

public class BuscarProducteService {

    private final ProducteRepository repository = new ProducteRepository();

    public List<Producte> buscarActius() {
        return repository.buscarActius();
    }

    public List<Producte> buscar(String text, boolean actius, boolean inactius) {
        return repository.buscar(text, actius, inactius);
    }

    public Producte buscarPerCodiONom(String text) {
        return repository.buscarPerCodiONom(text).orElse(null);
    }
}