package cat.informaticassa.icfact.iva.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.iva.exception.IvaJaExisteixException;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IvaServiceTest extends BaseRepositoryTest {
    private final IvaRepository repository = new IvaRepository();

    @Test
    void guardarIva() {
        Iva iva = Iva.builder()
                .nom("IVA Prova")
                .percentatge(new BigDecimal("15.00"))
                .build();

        new CrearIvaService().executar(iva);
        assertTrue(repository.buscarPerPercentatge(new BigDecimal("15.00")).isPresent());
    }

    @Test
    void guardarIvaQuanJaExisteix() {
        Iva iva = repository.buscarPerPercentatge(new BigDecimal("21.00")).orElseThrow();
        assertThrows(IvaJaExisteixException.class,
                () -> new CrearIvaService().executar(iva));
    }

    @Test
    void actualitzarIva() {
        Iva iva = repository.buscarPerPercentatge(new BigDecimal("10.00")).orElseThrow();
        iva.setNom("IVA Reduït");
        new ModificarIvaService().executar(iva);
        Iva resultat = repository.buscarPerPercentatge(new BigDecimal("10.00")).orElseThrow();
        assertEquals("IVA Reduït", resultat.getNom());
    }

    @Test
    void buscarIvaPerId() {
        Iva origen = repository.buscarPerPercentatge(new BigDecimal("21.00")).orElseThrow();
        Iva iva = new BuscarIvaService().buscarPerId(origen.getId());
        assertNotNull(iva);
    }

    @Test
    void buscarIvaPerPercentatge() {
        Iva iva = new BuscarIvaService().buscarPerPercentatge(new BigDecimal("21.00"));
        assertNotNull(iva);
    }

    @Test
    void desactivarIva() {
        Iva iva = repository.buscarPerPercentatge(new BigDecimal("4.00")).orElseThrow();
        new DesactivarIvaService().executar(iva);
        Iva resultat = repository.buscarPerIdIncloentInactius(iva.getId()).orElseThrow();
        assertFalse(resultat.isActiu());
    }

    @Test
    void activarIva() {
        Iva iva = repository.buscarPerPercentatge(new BigDecimal("4.00")).orElseThrow();
        new DesactivarIvaService().executar(iva);
        new ActivarIvaService().executar(iva);
        Iva resultat = repository.buscarPerId(iva.getId()).orElseThrow();
        assertTrue(resultat.isActiu());
    }

    @Test
    void buscarInactius() {
        Iva iva = repository.buscarPerPercentatge(new BigDecimal("4.00")).orElseThrow();
        repository.desactivar(iva);
        List<Iva> ivaInactius = repository.buscarInactius();
        assertEquals(1, ivaInactius.size());

    }
}