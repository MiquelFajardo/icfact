package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerarPdfFacturaServiceTest extends BaseRepositoryTest {

    private final GenerarPdfFacturaService service = new GenerarPdfFacturaService();
    private final FacturaRepository repository = new FacturaRepository();

    @Test
    void generarPdf() throws Exception {

        Factura factura = repository.buscarPerNumero("F2026000001")
                .orElseThrow();

        Path carpeta = Path.of("factures");
        Path fitxer = carpeta.resolve(factura.getNumero() + ".pdf");

        service.executar(factura.getId());

        assertTrue(Files.exists(fitxer));
        assertTrue(Files.size(fitxer) > 0);

        // Files.deleteIfExists(fitxer);
    }
}