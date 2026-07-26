package cat.informaticassa.icfact.pressupost.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PressupostRepositoryTest extends BaseRepositoryTest {

    private final PressupostRepository repository = new PressupostRepository();

    @Test
    void buscarPerNumero() {
        Optional<Pressupost> resultat = repository.buscarPerNumero("P2026000001");
        assertTrue(resultat.isPresent());
    }

    @Test
    void buscarPerData() {
        List<Pressupost> pressupostos = repository.buscarPerData(LocalDate.now());
        assertEquals(2, pressupostos.size());
    }

    @Test
    void buscarTots() {
        List<Pressupost> pressupostos = repository.buscarTots();
        assertEquals(2, pressupostos.size());
    }

    @Test
    void buscarTotsActius() {
        List<Pressupost> pressupostos = repository.buscarTots();
        assertEquals(2, pressupostos.size());
    }

    @Test
    void actualitzar() {
        Pressupost pressupost = repository.buscarPerNumero("P2026000001").orElseThrow();
        pressupost.setObservacions("Observacions modificades");
        repository.actualitzar(pressupost);
        Pressupost resultat = repository.buscarPerNumero("P2026000001").orElseThrow();
        assertEquals("Observacions modificades", resultat.getObservacions());
    }

    @Test
    void desactivar() {
        Pressupost pressupost = repository.buscarPerNumero("P2026000001").orElseThrow();
        repository.desactivar(pressupost);
        Pressupost resultat = repository.buscarPerIdIncloentInactius(pressupost.getId()).orElseThrow();
        assertFalse(resultat.isActiu());
    }

    @Test
    void activar() {
        Pressupost pressupost = repository.buscarPerNumero("P2026000001").orElseThrow();
        repository.desactivar(pressupost);
        repository.activar(pressupost);
        Pressupost resultat = repository.buscarPerIdIncloentInactius(pressupost.getId()).orElseThrow();
        assertTrue(resultat.isActiu());
    }
}