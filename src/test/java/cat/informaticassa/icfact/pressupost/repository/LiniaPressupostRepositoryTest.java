package cat.informaticassa.icfact.pressupost.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LiniaPressupostRepositoryTest extends BaseRepositoryTest {

    private final LiniaPressupostRepository repository = new LiniaPressupostRepository();
    private final PressupostRepository pressupostRepository = new PressupostRepository();

    @Test
    void buscarPerPressupost() {
        Long id = pressupostRepository
                .buscarPerNumero("P2026000001")
                .orElseThrow()
                .getId();
        List<LiniaPressupost> linies = repository.buscarPerPressupost(id);
        assertEquals(2, linies.size());
    }

    @Test
    void buscarTotes() {
        List<LiniaPressupost> linies = repository.buscarTots();
        assertEquals(2, linies.size());
    }

    @Test
    void buscarTotesActives() {
        List<LiniaPressupost> linies = repository.buscarTots();
        assertEquals(2, linies.size());
    }

    @Test
    void actualitzar() {
        LiniaPressupost linia = repository.buscarTots().getFirst();
        linia.setDescripcio("Descripció modificada");
        repository.actualitzar(linia);
        LiniaPressupost resultat = repository.buscarPerId(linia.getId()).orElseThrow();
        assertEquals("Descripció modificada", resultat.getDescripcio());
    }

    @Test
    void desactivar() {
        LiniaPressupost linia = repository.buscarTots().getFirst();
        repository.desactivar(linia);
        LiniaPressupost resultat = repository.buscarPerIdIncloentInactius(linia.getId()).orElseThrow();
        assertFalse(resultat.isActiu());
    }

    @Test
    void activar() {
        LiniaPressupost linia = repository.buscarTots().getFirst();
        repository.desactivar(linia);
        repository.activar(linia);
        LiniaPressupost resultat = repository.buscarPerIdIncloentInactius(linia.getId()).orElseThrow();
        assertTrue(resultat.isActiu());
    }
}