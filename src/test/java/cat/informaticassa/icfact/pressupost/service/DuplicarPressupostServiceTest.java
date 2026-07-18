package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicarPressupostServiceTest extends BaseRepositoryTest {

    private final DuplicarPressupostService service = new DuplicarPressupostService();
    private final PressupostRepository repository = new PressupostRepository();

    @Test
    void duplicarPressupost() {

        Pressupost original = repository.buscarPerNumero("P2026000001")
                .orElseThrow();

        Pressupost copia = service.executar(original.getId());

        assertNotEquals(original.getId(), copia.getId());
        assertNotEquals(original.getNumero(), copia.getNumero());

        assertEquals(original.getClient().getId(), copia.getClient().getId());
        assertEquals(original.getLinies().size(), copia.getLinies().size());

        assertEquals(0, original.getSubtotal().compareTo(copia.getSubtotal()));
        assertEquals(0, original.getIva().compareTo(copia.getIva()));
        assertEquals(0, original.getTotal().compareTo(copia.getTotal()));
    }
}