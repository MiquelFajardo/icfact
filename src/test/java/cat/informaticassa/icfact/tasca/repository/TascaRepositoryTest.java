package cat.informaticassa.icfact.tasca.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.tasca.model.Tasca;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TascaRepositoryTest extends BaseRepositoryTest {

    private final TascaRepository repository = new TascaRepository();

    @Test
    void buscarPerId() {
        Tasca tasca = repository.buscarPendents().getFirst();
        Optional<Tasca> resultat = repository.buscarPerId(tasca.getId());
        assertTrue(resultat.isPresent());
    }

    @Test
    void buscarTotes() {
        List<Tasca> tasques = repository.buscarTots();
        assertEquals(3, tasques.size());
    }

    @Test
    void buscarPendents() {
        List<Tasca> tasques = repository.buscarPendents();
        assertEquals(3, tasques.size());
    }

    @Test
    void buscarFetes() {
        List<Tasca> tasques = repository.buscarFetes();
        assertTrue(tasques.isEmpty());
    }

    @Test
    void actualitzar() {
        Tasca tasca = repository.buscarPendents().getFirst();
        tasca.setTitol("Tasca modificada");
        repository.actualitzar(tasca);
        Tasca resultat = repository.buscarPerId(tasca.getId()).orElseThrow();
        assertEquals("Tasca modificada", resultat.getTitol());
    }

    @Test
    void eliminar() {
        Tasca tasca = repository.buscarPendents().getFirst();
        repository.desactivar(tasca);
        assertTrue(repository.buscarPerId(tasca.getId()).isEmpty());
        List<Tasca> inactives = repository.buscarInactius();
        assertEquals(1, inactives.size());
        assertFalse(inactives.getFirst().isActiu());
    }

    @Test
    void activar() {
        Tasca tasca = repository.buscarPendents().getFirst();
        repository.desactivar(tasca);
        Tasca inactiva = repository.buscarInactius().getFirst();
        repository.activar(inactiva);
        Tasca activa = repository.buscarPerId(inactiva.getId()).orElseThrow();
        assertTrue(activa.isActiu());
    }

}