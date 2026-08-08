package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.pdf.service.ObrirPdfService;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.ui.util.Alerta;

import java.nio.file.Files;
import java.nio.file.Path;

public class ObrirPdfFacturaService {
    private final GenerarPdfFacturaService generarPdfService = new GenerarPdfFacturaService();
    private final ObrirPdfService obrirPdfService = new ObrirPdfService();

    public void executar(Factura factura) {
        Path pdf = Path.of("factures",factura.getNumero() + ".pdf");
        if (!Files.exists(pdf)) {
            Alerta.informacio(null,"El PDF no existeix.\n\nEs tornarà a generar.");
            pdf = generarPdfService.executar(factura.getId());
        }
        obrirPdfService.executar(pdf);
    }
}