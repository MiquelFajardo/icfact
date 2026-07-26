package cat.informaticassa.icfact.geografia.service;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.repository.PaisRepository;

import java.util.List;

public class BuscarPaisosService {

    private final PaisRepository repository = new PaisRepository();

    public List<Pais> executar() {
        return repository.buscarTots();
    }

}