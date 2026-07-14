package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecalcularPressupostServiceTest extends BaseRepositoryTest {

    private final PressupostRepository repository = new PressupostRepository();
    private final RecalcularPressupostService service = new RecalcularPressupostService();

    @Test
    void recalcularPressupost() {
        Pressupost pressupost = repository
                .buscarPerNumero("P2026000001")
                .orElseThrow();
        service.executar(pressupost);
        assertEquals(0, pressupost.getSubtotal().compareTo(new BigDecimal("124.00")));
        assertEquals(0, pressupost.getIva().compareTo(new BigDecimal("26.04")));
        assertEquals(0, pressupost.getTotal().compareTo(new BigDecimal("150.04")));

    }
}