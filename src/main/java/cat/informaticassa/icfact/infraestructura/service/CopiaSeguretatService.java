package cat.informaticassa.icfact.infraestructura.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CopiaSeguretatService {
    private final Path baseDades = Path.of("empresa.db");
    public void executar(Path destinacio) {
        if (destinacio == null) {
            throw new IllegalArgumentException("No s'ha seleccionat cap destinació.");
        }

        if (!Files.exists(baseDades)) {
            throw new IllegalStateException("No s'ha trobat la base de dades.");
        }

        try {
            Files.copy(baseDades, destinacio, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("No s'ha pogut crear la còpia de seguretat.", e);
        }
    }
}