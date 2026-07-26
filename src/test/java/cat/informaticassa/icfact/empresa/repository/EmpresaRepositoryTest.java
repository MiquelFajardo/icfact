package cat.informaticassa.icfact.empresa.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.empresa.model.Empresa;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmpresaRepositoryTest extends BaseRepositoryTest {

    private final EmpresaRepository repository = new EmpresaRepository();

    @Test
    void buscarEmpresa() {
        Optional<Empresa> empresa = repository.buscar();
        assertTrue(empresa.isPresent());
        assertEquals("Manel Serra", empresa.get().getNom());
    }

    @Test
    void actualitzarEmpresa() {
        Empresa empresa = repository.buscar().orElseThrow();
        empresa.setTelefon("972000000");
        repository.actualitzar(empresa);
        Optional<Empresa> resultat = repository.buscar();
        assertTrue(resultat.isPresent());
        assertEquals("972000000", resultat.get().getTelefon());
    }
}