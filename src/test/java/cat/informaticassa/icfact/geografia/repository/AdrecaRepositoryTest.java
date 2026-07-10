package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AdrecaRepositoryTest extends BaseRepositoryTest {

    private final AdrecaRepository repository = new AdrecaRepository();
    private final PoblacioRepository poblacioRepository = new PoblacioRepository();

    @Test
    void buscarPerId() {
        Adreca adreca = repository.buscarTots().getFirst();
        Optional<Adreca> resultat = repository.buscarPerId(adreca.getId());
        assertTrue(resultat.isPresent());
        assertEquals(adreca.getId(), resultat.get().getId());
    }

    @Test
    void buscarTotesLesAdreces() {
        List<Adreca> adreces = repository.buscarTots();
        assertEquals(3, adreces.size());
    }

    @Test
    void buscarPerPoblacio() {
        Poblacio poblacio = poblacioRepository
                .buscarPerNomIProvincia(
                        "Cassà de la Selva",
                        new ProvinciaRepository().buscarPerCodi("GI").orElseThrow())
                .orElseThrow();
        List<Adreca> adreces = repository.buscarPerPoblacio(poblacio);
        assertEquals(1, adreces.size());
    }

    @Test
    void actualitzarAdreca() {
        Adreca adreca = repository.buscarTots().getFirst();
        adreca.setCarrer("Carrer Major");
        repository.actualitzar(adreca);
        Optional<Adreca> resultat = repository.buscarPerId(adreca.getId());
        assertTrue(resultat.isPresent());
        assertEquals("Carrer Major", resultat.get().getCarrer());
    }
}