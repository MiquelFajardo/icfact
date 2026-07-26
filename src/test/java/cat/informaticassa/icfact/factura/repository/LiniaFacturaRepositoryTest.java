package cat.informaticassa.icfact.factura.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LiniaFacturaRepositoryTest extends BaseRepositoryTest {

    private final LiniaFacturaRepository repository = new LiniaFacturaRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    @Test
    void buscarPerFactura() {
        Long id = facturaRepository
                .buscarPerNumero("F2026000001")
                .orElseThrow()
                .getId();
        List<LiniaFactura> linies = repository.buscarPerFactura(id);
        assertEquals(2, linies.size());
    }

    @Test
    void buscarTotes() {
        List<LiniaFactura> linies = repository.buscarTots();
        assertEquals(2, linies.size());
    }

    @Test
    void buscarTotesActives() {
        List<LiniaFactura> linies = repository.buscarTots();
        assertEquals(2, linies.size());
    }

    @Test
    void actualitzar() {
        LiniaFactura linia = repository.buscarTots().getFirst();
        linia.setDescripcio("Descripció modificada");
        repository.actualitzar(linia);
        LiniaFactura resultat = repository.buscarPerId(linia.getId()).orElseThrow();
        assertEquals("Descripció modificada", resultat.getDescripcio());
    }

    @Test
    void desactivar() {
        LiniaFactura linia = repository.buscarTots().getFirst();
        repository.desactivar(linia);
        LiniaFactura resultat = repository.buscarPerIdIncloentInactius(linia.getId()).orElseThrow();
        assertFalse(resultat.isActiu());
    }

    @Test
    void activar() {
        LiniaFactura linia = repository.buscarTots().getFirst();
        repository.desactivar(linia);
        repository.activar(linia);
        LiniaFactura resultat = repository.buscarPerId(linia.getId()).orElseThrow();
        assertTrue(resultat.isActiu());
    }
}