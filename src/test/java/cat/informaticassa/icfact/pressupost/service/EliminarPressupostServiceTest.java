package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.repository.LiniaPressupostRepository;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliminarPressupostServiceTest extends BaseRepositoryTest {

    private final EliminarPressupostService service = new EliminarPressupostService();
    private final PressupostRepository repository = new PressupostRepository();
    private final LiniaPressupostRepository liniaRepository = new LiniaPressupostRepository();

    @Test
    void eliminarPressupost() {

        service.executar(1L);

        assertTrue(repository.buscarPerId(1L).isEmpty());

        assertFalse(repository.buscarPerIdIncloentInactius(1L)
                .orElseThrow()
                .isActiu());

        liniaRepository.buscarPerPressupost(1L)
                .forEach(l -> fail("No hi hauria d'haver línies actives."));
    }
}