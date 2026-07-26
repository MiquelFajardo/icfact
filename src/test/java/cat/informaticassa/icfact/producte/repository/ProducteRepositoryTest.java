package cat.informaticassa.icfact.producte.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.producte.model.Producte;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProducteRepositoryTest extends BaseRepositoryTest {

    private final ProducteRepository repository = new ProducteRepository();

    @Test
    void buscarPerCodi() {
        Optional<Producte> resultat = repository.buscarPerCodi("P0001");
        assertTrue(resultat.isPresent());
        assertEquals("Manteniment informàtic", resultat.get().getNom());
    }

    @Test
    void buscarPerNom() {
        List<Producte> productes = repository.buscarPerNom("Windows");
        assertEquals(1, productes.size());
        assertEquals("P0002", productes.getFirst().getCodi());
    }

    @Test
    void buscarTotsElsProductes() {
        List<Producte> productes = repository.buscarTots();
        assertEquals(3, productes.size());
    }

    @Test
    void buscarProductesActius() {
        List<Producte> productes = repository.buscarTots();
        assertEquals(3, productes.size());
    }

    @Test
    void buscarTotsInactius() {
        Producte producte = repository.buscarPerCodi("P0001").orElseThrow();
        repository.desactivar(producte);
        List<Producte> productes = repository.buscarInactius();
        assertEquals(1, productes.size());
    }

    @Test
    void actualitzarProducte() {
        Producte producte = repository.buscarPerCodi("P0001").orElseThrow();
        producte.setPreu(producte.getPreu().add(java.math.BigDecimal.TEN));
        repository.actualitzar(producte);
        Optional<Producte> resultat = repository.buscarPerCodi("P0001");
        assertTrue(resultat.isPresent());
        assertEquals(0, resultat.get().getPreu().compareTo(new java.math.BigDecimal("45.00")));
    }

    @Test
    void desactivarProducte() {
        Producte producte = repository.buscarPerCodi("P0002").orElseThrow();
        repository.desactivar(producte);
        Producte resultat = repository.buscarPerIdIncloentInactius(producte.getId()).orElseThrow();
        assertFalse(resultat.isActiu());
    }

    @Test
    void activarProducte() {
        Producte producte = repository.buscarPerCodi("P0003").orElseThrow();
        repository.desactivar(producte);
        repository.activar(producte);
        Producte resultat = repository.buscarPerId(producte.getId()).orElseThrow();
        assertTrue(resultat.isActiu());
    }
}