package cat.informaticassa.icfact.geografia.service.poblacio;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.repository.PoblacioRepository;

import java.util.List;

public class BuscarPoblacionsService {

    private final PoblacioRepository repository = new PoblacioRepository();

    public List<Poblacio> executar() {
        return repository.buscarTots();
    }
}