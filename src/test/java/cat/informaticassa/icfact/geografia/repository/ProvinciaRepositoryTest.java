package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProvinciaRepositoryTest extends BaseRepositoryTest {

    private final ProvinciaRepository repository = new ProvinciaRepository();
    private final PaisRepository paisRepository = new PaisRepository();

    @Test
    void buscarProvinciaPerCodi() {
        Optional<Provincia> resultat = repository.buscarPerCodi("GI");
        assertTrue(resultat.isPresent());
        assertEquals("Girona", resultat.get().getNom());
    }

    @Test
    void buscarProvinciaPerCodiInexistent() {
        Optional<Provincia> resultat = repository.buscarPerCodi("XX");
        assertTrue(resultat.isEmpty());
    }

    @Test
    void buscarTotesLesProvincies() {
        List<Provincia> provincies = repository.buscarTots();
        assertEquals(2, provincies.size());
    }

    @Test
    void buscarProvinciesPerPais() {
        Pais pais = paisRepository.buscarPerCodiIso("ES").orElseThrow();
        List<Provincia> provincies = repository.buscarPerPais(pais);
        assertEquals(2, provincies.size());
    }

    @Test
    void actualitzarProvincia() {
        Provincia provincia = repository.buscarPerCodi("B").orElseThrow();
        provincia.setNom("Barcelona Modificada");
        repository.actualitzar(provincia);
        Optional<Provincia> resultat = repository.buscarPerCodi("B");
        assertTrue(resultat.isPresent());
        assertEquals("Barcelona Modificada", resultat.get().getNom());
    }
}