package cat.informaticassa.icfact.pressupost.service;


import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerarPdfPressupostServiceTest extends BaseRepositoryTest {

    private final GenerarPdfPressupostService service = new GenerarPdfPressupostService();
    private final PressupostRepository repository = new PressupostRepository();

    @Test
    void generarPdf() throws Exception {

        Pressupost pressupost = repository.buscarPerNumero("P2026000001")
                .orElseThrow();

        Path carpeta = Path.of("pressupostos");
   //     Path pdf = Path.of("P2026000001.pdf");

        Path fitxer = carpeta.resolve(pressupost.getNumero() + ".pdf");

        service.executar(pressupost.getId());

        assertTrue(Files.exists(fitxer));
        assertTrue(Files.size(fitxer) > 0);

      //  Files.deleteIfExists(pdf);
    }
}
