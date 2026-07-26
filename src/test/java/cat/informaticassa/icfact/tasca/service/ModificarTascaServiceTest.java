package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModificarTascaServiceTest extends BaseRepositoryTest {

    private final ModificarTascaService service = new ModificarTascaService();
    private final TascaRepository repository = new TascaRepository();

    @Test
    void modificarTasca() {
        Tasca tasca = repository.buscarPendents().getFirst();
        tasca.setTitol("Tasca modificada");
        service.executar(tasca);
        Tasca resultat = repository.buscarPerId(tasca.getId()).orElseThrow();
        assertEquals("Tasca modificada", resultat.getTitol());
    }
}