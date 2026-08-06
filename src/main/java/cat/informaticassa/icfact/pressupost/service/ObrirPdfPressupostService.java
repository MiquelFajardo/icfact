package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pdf.service.ObrirPdfService;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.ui.util.Alerta;

import java.nio.file.Files;
import java.nio.file.Path;

public class ObrirPdfPressupostService {
    private final GenerarPdfPressupostService generarPdfService = new GenerarPdfPressupostService();
    private final ObrirPdfService obrirPdfService = new ObrirPdfService();

    public void executar(Pressupost pressupost) {
        Path pdf = Path.of("pressupostos",pressupost.getNumero() + ".pdf");
        if (!Files.exists(pdf)) {
            Alerta.informacio( null,"El PDF no existeix.\n\nEs tornarà a generar.");
            pdf = generarPdfService.executar(pressupost);
        }
        obrirPdfService.executar(pdf);
    }
}