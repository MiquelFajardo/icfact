package cat.informaticassa.icfact.iva.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.iva.model.Iva;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class IvaRepositoryTest extends BaseRepositoryTest {

    private final IvaRepository repository = new IvaRepository();

    @Test
    void buscarPerPercentatge() {
        Optional<Iva> resultat = repository.buscarPerPercentatge(new BigDecimal("21.00"));
        assertTrue(resultat.isPresent());
        assertEquals("General", resultat.get().getNom());
    }

    @Test
    void buscarTots() {
        List<Iva> iva = repository.buscarTots();
        assertEquals(4, iva.size());
    }


    @Test
    void actualitzar() {
        Iva iva = repository.buscarPerPercentatge(new BigDecimal("10.00")).orElseThrow();
        iva.setNom("Reduït Modificat");
        repository.actualitzar(iva);
        Optional<Iva> resultat = repository.buscarPerPercentatge(new BigDecimal("10.00"));
        assertTrue(resultat.isPresent());
        assertEquals("Reduït Modificat", resultat.get().getNom());
    }

    @Test
    void desactivar() {
        Iva iva = repository.buscarPerPercentatge(new BigDecimal("4.00")).orElseThrow();
        repository.desactivar(iva);
        Iva resultat = repository.buscarPerIdIncloentInactius(iva.getId()).orElseThrow();
        assertFalse(resultat.isActiu());
    }

    @Test
    void activar() {
        Iva iva = repository.buscarPerPercentatge(new BigDecimal("4.00")).orElseThrow();
        repository.desactivar(iva);
        repository.activar(iva);
        Iva resultat = repository.buscarPerId(iva.getId()).orElseThrow();
        assertTrue(resultat.isActiu());
    }
}