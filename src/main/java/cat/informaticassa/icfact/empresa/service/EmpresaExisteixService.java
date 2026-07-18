package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;

public class EmpresaExisteixService {

    private final EmpresaRepository repository = new EmpresaRepository();

    public boolean executar() {
        return repository.buscar().isPresent();
    }

}