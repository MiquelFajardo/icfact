package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.exception.EmpresaNoExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import cat.informaticassa.icfact.empresa.service.validar.ValidarEmpresa;

import java.time.LocalDateTime;

public class ModificarEmpresaService {

    private final EmpresaRepository repository = new EmpresaRepository();
    private final ValidarEmpresa validarEmpresa = new ValidarEmpresa();

    public void executar(Empresa empresa) {
        validarEmpresa.executar(empresa);
        Empresa empresaActual = repository.buscar().orElseThrow(() -> new EmpresaNoExisteixException("No existeix cap empresa."));
        empresaActual.actualitzarDades(empresa);
        empresaActual.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(empresaActual);
    }
}