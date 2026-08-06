package cat.informaticassa.icfact.pdf.service;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ObrirPdfService {
    public void executar(Path pdf) {
        if (pdf == null) {
            throw new IllegalArgumentException("No s'ha indicat cap PDF.");
        }

        if (!Files.exists(pdf)) {
            throw new IllegalArgumentException("El fitxer PDF no existeix.");
        }

        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Desktop.getDesktop().open(pdf.toFile());
                return;
            }

            if (os.contains("mac")) {
                new ProcessBuilder("open", pdf.toString()).start();
                return;
            }

            if (os.contains("linux")) {
                new ProcessBuilder("xdg-open", pdf.toString()).start();
                return;
            }

            throw new RuntimeException("Sistema operatiu no suportat.");

        } catch (IOException e) {
            throw new RuntimeException("No s'ha pogut obrir el PDF.", e);
        }
    }
}