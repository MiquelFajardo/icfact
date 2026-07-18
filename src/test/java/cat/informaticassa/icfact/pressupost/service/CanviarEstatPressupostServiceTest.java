package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CanviarEstatPressupostServiceTest extends BaseRepositoryTest {

    private final CanviarEstatPressupostService service = new CanviarEstatPressupostService();
    private final PressupostRepository repository = new PressupostRepository();

    @Test
    void canviarEstatPressupost() {

        Pressupost pressupost = repository.buscarPerNumero("P2026000001")
                .orElseThrow();

        service.executar(pressupost.getId(), EstatPressupost.ACCEPTAT);

        Pressupost actualitzat = repository.buscarPerId(pressupost.getId())
                .orElseThrow();

        assertEquals(EstatPressupost.ACCEPTAT, actualitzat.getEstat());
    }
}