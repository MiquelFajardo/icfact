package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PoblacioRepositoryTest extends BaseRepositoryTest {
    private final PoblacioRepository repository = new PoblacioRepository();
    private final ProvinciaRepository provinciaRepository = new ProvinciaRepository();

    @Test
    void buscarPoblacioPerNomIProvincia() {
        Provincia provincia = provinciaRepository.buscarPerCodi("GI").orElseThrow();
        Optional<Poblacio> resultat = repository.buscarPerNomIProvincia("Cassà de la Selva", provincia);
        assertTrue(resultat.isPresent());
        assertEquals("Cassà de la Selva", resultat.get().getNom());
    }

    @Test
    void buscarPoblacioPerNomIProvinciaInexistent() {
        Provincia provincia = provinciaRepository.buscarPerCodi("GI").orElseThrow();
        Optional<Poblacio> resultat = repository.buscarPerNomIProvincia("Girona", provincia);
        assertTrue(resultat.isEmpty());
    }

    @Test
    void buscarTotesLesPoblacions() {
        List<Poblacio> poblacions = repository.buscarTots();
        assertEquals(3, poblacions.size());
    }

    @Test
    void buscarPoblacionsPerProvincia() {
        Provincia provincia = provinciaRepository.buscarPerCodi("GI").orElseThrow();
        List<Poblacio> poblacions = repository.buscarPerProvincia(provincia);
        assertEquals(2, poblacions.size());
    }

    @Test
    void actualitzarPoblacio() {
        Provincia provincia = provinciaRepository.buscarPerCodi("GI").orElseThrow();
        Poblacio poblacio = repository.buscarPerNomIProvincia("Llagostera", provincia).orElseThrow();
        poblacio.setNom("Llagostera Modificada");
        repository.actualitzar(poblacio);
        Optional<Poblacio> resultat = repository.buscarPerNomIProvincia("Llagostera Modificada", provincia);
        assertTrue(resultat.isPresent());
    }
}