package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivarPressupostServiceTest extends BaseRepositoryTest {

    private final PressupostRepository repository = new PressupostRepository();
    private final EliminarPressupostService eliminarService = new EliminarPressupostService();
    private final ActivarPressupostService activarService = new ActivarPressupostService();

    @Test
    void activarPressupost() {

        Pressupost pressupost = repository.buscarPerNumero("P2026000001")
                .orElseThrow();

        eliminarService.executar(pressupost.getId());

        Pressupost desactivat = repository.buscarPerIdIncloentInactius(pressupost.getId())
                .orElseThrow();

        assertFalse(desactivat.isActiu());
        assertTrue(desactivat.getLinies().stream().noneMatch(LiniaPressupost::isActiu));

        activarService.executar(pressupost.getId());

        Pressupost resultat = repository.buscarPerIdAmbLinies(pressupost.getId())
                .orElseThrow();

        assertTrue(resultat.isActiu());
        assertTrue(resultat.getLinies().stream().allMatch(LiniaPressupost::isActiu));
    }
}