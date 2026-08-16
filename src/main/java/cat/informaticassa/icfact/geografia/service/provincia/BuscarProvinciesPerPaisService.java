package cat.informaticassa.icfact.geografia.service.provincia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.repository.ProvinciaRepository;

import java.util.List;

public class BuscarProvinciesPerPaisService {

    private final ProvinciaRepository repository = new ProvinciaRepository();

    public List<Provincia> executar(Pais pais) {
        if (pais == null || pais.getId() == null) {
            return List.of();
        }

        return repository.buscarPerPaisId(pais.getId());
    }
}