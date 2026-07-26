package cat.informaticassa.icfact.pagament.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PagamentRepositoryTest extends BaseRepositoryTest {

    private final PagamentRepository repository = new PagamentRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    @Test
    void buscarTots() {
        assertEquals(1, repository.buscarTots().size());
    }

    @Test
    void buscarPerFactura() {

        Factura factura = facturaRepository.buscarPerNumero("F2026000001").orElseThrow();

        assertEquals(1, repository.buscarPerFactura(factura).size());
    }

    @Test
    void calcularImportPagat() {

        Factura factura = facturaRepository.buscarPerNumero("F2026000001").orElseThrow();

        BigDecimal importPagat = repository.calcularImportPagat(factura);

        assertTrue(importPagat.compareTo(new BigDecimal("250.00")) == 0);
    }

    @Test
    void eliminar() {
        Pagament pagament = repository.buscarTots().getFirst();
        repository.desactivar(pagament);
        assertTrue(repository.buscarPerId(pagament.getId()).isEmpty());
    }

    @Test
    void activar() {
        Pagament pagament = repository.buscarTots().getFirst();
        repository.desactivar(pagament);
        repository.activar(pagament);
        assertTrue(repository.buscarPerId(pagament.getId()).isPresent());
    }

    @Test
    void calcularImportPagatSensePagaments() {
        Factura factura = facturaRepository.buscarPerNumero("F2026000001").orElseThrow();
        Pagament pagament = repository.buscarTots().getFirst();
        repository.desactivar(pagament);
        assertEquals(BigDecimal.ZERO, repository.calcularImportPagat(factura));
    }
}