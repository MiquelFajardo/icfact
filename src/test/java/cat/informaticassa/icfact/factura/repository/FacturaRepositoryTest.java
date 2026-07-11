package cat.informaticassa.icfact.factura.repository;


import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.Factura;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaRepositoryTest extends BaseRepositoryTest {

    private final FacturaRepository repository = new FacturaRepository();

    @Test
    void buscarPerNumero() {
        Optional<Factura> resultat = repository.buscarPerNumero("F-2026-000001");
        assertTrue(resultat.isPresent());
    }

    @Test
    void buscarPerData() {
        List<Factura> factures = repository.buscarPerData(LocalDate.now());
        assertEquals(2, factures.size());
    }

    @Test
    void buscarTotes() {
        List<Factura> factures = repository.buscarTots();
        assertEquals(2, factures.size());
    }

    @Test
    void buscarTotesActives() {
        List<Factura> factures = repository.buscarTotsActius();
        assertEquals(2, factures.size());
    }

    @Test
    void actualitzar() {
        Factura factura = repository.buscarPerNumero("F-2026-000001").orElseThrow();
        factura.setObservacions("Factura modificada");
        repository.actualitzar(factura);
        Factura resultat = repository.buscarPerNumero("F-2026-000001").orElseThrow();
        assertEquals("Factura modificada", resultat.getObservacions());
    }

    @Test
    void desactivar() {
        Factura factura = repository.buscarPerNumero("F-2026-000001").orElseThrow();
        repository.eliminar(factura);
        Factura resultat = repository.buscarPerNumero("F-2026-000001").orElseThrow();
        assertFalse(resultat.getActiu());
    }

    @Test
    void activar() {
        Factura factura = repository.buscarPerNumero("F-2026-000001").orElseThrow();
        repository.eliminar(factura);
        repository.activar(factura);
        Factura resultat = repository.buscarPerNumero("F-2026-000001").orElseThrow();
        assertTrue(resultat.getActiu());
    }
}