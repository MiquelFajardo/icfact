package cat.informaticassa.icfact.geografia.service;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.repository.PoblacioRepository;

import java.util.List;

public class BuscarPoblacionsPerProvinciaService {

    private final PoblacioRepository repository = new PoblacioRepository();

    public List<Poblacio> executar(Provincia provincia) {
        return repository.buscarPerProvincia(provincia);
    }

}