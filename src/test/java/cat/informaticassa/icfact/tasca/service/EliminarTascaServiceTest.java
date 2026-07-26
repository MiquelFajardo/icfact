package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EliminarTascaServiceTest extends BaseRepositoryTest {

    private final EliminarTascaService eliminarService = new EliminarTascaService();
    private final TascaRepository repository = new TascaRepository();

    @Test
    void eliminarTasca() {
        Tasca tasca = repository.buscarPendents().getFirst();
        eliminarService.executar(tasca);
        Tasca eliminada = repository.buscarInactius().getFirst();
        assertFalse(eliminada.isActiu());
    }
}