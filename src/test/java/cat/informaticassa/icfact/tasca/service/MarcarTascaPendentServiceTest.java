package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class MarcarTascaPendentServiceTest extends BaseRepositoryTest {

    private final MarcarTascaPendentService service = new MarcarTascaPendentService();
    private final TascaRepository repository = new TascaRepository();

    @Test
    void marcarComPendent() {
        Tasca tasca = repository.buscarPendents().getFirst();
        tasca.setFeta(true);
        repository.actualitzar(tasca);
        service.executar(tasca);
        Tasca resultat = repository.buscarPerId(tasca.getId()).orElseThrow();
        assertFalse(resultat.isFeta());
    }
}