package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.exception.EmpresaJaExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;

public class GuardarEmpresaService {
    private final EmpresaRepository repository = new EmpresaRepository();

    public void executar(Empresa empresa) {
        if (repository.buscar().isPresent()) {
            throw new EmpresaJaExisteixException(
                    "Ja existeix una empresa registrada."
            );
        }
        repository.guardar(empresa);
    }
}