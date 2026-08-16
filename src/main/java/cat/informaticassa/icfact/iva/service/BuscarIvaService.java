package cat.informaticassa.icfact.iva.service;

import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;

import java.util.List;

public class BuscarIvaService {
    private final IvaRepository repository = new IvaRepository();

    public List<Iva> buscarTots() {
        return repository.buscarTots();
    }

    public List<Iva> buscar(String text, boolean actius, boolean inactius) {
        return repository.buscar(text, actius, inactius);
    }

    public List<Iva> buscarActius() {
        return repository.buscarTots();
    }

    public List<Iva> buscarInactius() {
        return repository.buscarInactius();
    }

}