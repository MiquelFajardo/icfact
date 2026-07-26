package cat.informaticassa.icfact.formaPagament.repository;



import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.testdata.TestDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormaPagamentRepositoryTest extends BaseRepositoryTest {

    private final FormaPagamentRepository repository = new FormaPagamentRepository();


    @Test
    void buscarPerNom() {
        FormaPagament forma = repository.buscarPerNom("Transferència").orElseThrow();
        assertEquals("Transferència", forma.getNom());
    }

    @Test
    void buscarTots() {
        assertEquals(5, repository.buscarTots().size());
    }

    @Test
    void eliminar() {
        FormaPagament forma = repository.buscarPerNom("Bizum").orElseThrow();
        repository.desactivar(forma);
        assertTrue(repository.buscarPerNom("Bizum").isEmpty());    }

    @Test
    void activar() {
        FormaPagament forma = repository.buscarPerNom("Bizum").orElseThrow();
        repository.desactivar(forma);
        repository.activar(forma);
        assertTrue(repository.buscarPerNom("Bizum").isPresent());
    }
}