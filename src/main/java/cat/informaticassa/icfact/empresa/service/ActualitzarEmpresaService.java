package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.exception.EmpresaNoExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;

public class ActualitzarEmpresaService {
    private final EmpresaRepository repository = new EmpresaRepository();

    public void executar(Empresa empresa) {

        Empresa empresaActual = repository.buscar()
                .orElseThrow(() ->
                        new EmpresaNoExisteixException("No existeix cap empresa."));
        empresa.setId(empresaActual.getId());
        repository.actualitzar(empresa);
    }
}
