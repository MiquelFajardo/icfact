package cat.informaticassa.icfact.geografia.service;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.repository.PaisRepository;

public class CrearPaisService {

    private final PaisRepository repository = new PaisRepository();
    private final ValidarPais validar = new ValidarPais();

    public void executar(Pais pais) {
        validar.executar(pais);
        repository.guardar(pais);
    }
}