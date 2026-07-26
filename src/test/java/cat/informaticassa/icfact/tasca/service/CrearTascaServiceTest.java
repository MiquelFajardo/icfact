package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrearTascaServiceTest extends BaseRepositoryTest {

    private final CrearTascaService crearService = new CrearTascaService();
    private final TascaRepository repository = new TascaRepository();

    @Test
    void crearTasca() {
        int abans = repository.buscarTots().size();
        Tasca tasca = new Tasca();
        tasca.setTitol("Nova tasca");
        crearService.executar(tasca);
        int despres = repository.buscarTots().size();
        assertEquals(abans + 1, despres);
    }
}