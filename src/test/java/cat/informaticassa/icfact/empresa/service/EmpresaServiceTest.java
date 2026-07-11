package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.empresa.exception.EmpresaJaExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmpresaServiceTest extends BaseRepositoryTest {

    private final GuardarEmpresaService service = new GuardarEmpresaService();
    private final EmpresaRepository repository = new EmpresaRepository();

    @Test
    void guardarEmpresaQuanJaExisteix() {
        Empresa empresa = repository.buscar().orElseThrow();
        assertThrows(EmpresaJaExisteixException.class, () ->
                service.executar(empresa)
        );
    }

    @Test
    void actualitzarEmpresa() {
        Empresa empresa = repository.buscar().orElseThrow();
        empresa.setTelefon("972123456");
        new ActualitzarEmpresaService().executar(empresa);
        Empresa resultat = repository.buscar().orElseThrow();
        assertEquals("972123456", resultat.getTelefon());
    }

    @Test
    void buscarEmpresa() {
        Empresa empresa = new BuscarEmpresaService().executar();
        assertNotNull(empresa);
    }
}