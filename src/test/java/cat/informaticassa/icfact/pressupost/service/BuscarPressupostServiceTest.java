package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuscarPressupostServiceTest extends BaseRepositoryTest {

    private final BuscarPressupostService service = new BuscarPressupostService();

    @Test
    void buscarPressupost() {

        Pressupost pressupost = service.executar(1L);

        assertNotNull(pressupost);
        assertEquals("P2026000001", pressupost.getNumero());
    }
}