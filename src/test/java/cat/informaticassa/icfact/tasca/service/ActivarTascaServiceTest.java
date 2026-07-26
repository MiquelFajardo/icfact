package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivarTascaServiceTest extends BaseRepositoryTest {

    private final ActivarTascaService activarService = new ActivarTascaService();
    private final EliminarTascaService eliminarService = new EliminarTascaService();
    private final TascaRepository repository = new TascaRepository();

    @Test
    void activarTasca() {
        Tasca tasca = repository.buscarPendents().getFirst();
        eliminarService.executar(tasca);
        activarService.executar(tasca);
        Tasca activada = repository.buscarPerId(tasca.getId()).orElseThrow();
        assertTrue(activada.isActiu());
    }
}