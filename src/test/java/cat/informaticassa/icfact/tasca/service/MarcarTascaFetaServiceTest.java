package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MarcarTascaFetaServiceTest extends BaseRepositoryTest {

    private final MarcarTascaFetaService service = new MarcarTascaFetaService();
    private final TascaRepository repository = new TascaRepository();

    @Test
    void marcarComFeta() {
        Tasca tasca = repository.buscarPendents().getFirst();
        service.executar(tasca);
        Tasca resultat = repository.buscarPerId(tasca.getId()).orElseThrow();
        assertTrue(resultat.isFeta());
    }
}