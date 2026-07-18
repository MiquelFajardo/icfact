package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.exception.EmpresaNoExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;

public class BuscarEmpresaService {
    private final EmpresaRepository repository = new EmpresaRepository();

    public Empresa executar() {
        return repository.buscar().orElseThrow(() -> new EmpresaNoExisteixException("No existeix cap empresa."));
    }
}
