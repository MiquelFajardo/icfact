package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.geografia.model.Pais;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PaisRepositoryTest extends BaseRepositoryTest {

    private final PaisRepository repository = new PaisRepository();

    @Test
    void buscarPaisPerCodiIso() {
        Optional<Pais> resultat = repository.buscarPerCodiIso("ES");

        assertTrue(resultat.isPresent());
        assertEquals("Espanya", resultat.get().getNom());
    }

    @Test
    void buscarPaisPerCodiIsoInexistent() {
        Optional<Pais> resultat = repository.buscarPerCodiIso("XX");

        assertTrue(resultat.isEmpty());
    }

    @Test
    void buscarTotsElsPaisos() {
        List<Pais> paisos = repository.buscarTots();

        assertEquals(3, paisos.size());
    }

    @Test
    void actualitzarPais() {
        Pais pais = repository.buscarPerCodiIso("FR").orElseThrow();
        pais.setNom("France");
        repository.actualitzar(pais);
        Optional<Pais> resultat = repository.buscarPerCodiIso("FR");

        assertTrue(resultat.isPresent());
        assertEquals("France", resultat.get().getNom());
    }
}
