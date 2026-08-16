package cat.informaticassa.icfact.infraestructura.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class RestaurarCopiaSeguretatService {
    private final Path baseDades = Path.of("empresa.db");

    public void executar(Path copia) {
        if (copia == null) {
            throw new IllegalArgumentException("No s'ha seleccionat cap còpia de seguretat.");
        }

        if (!Files.exists(copia)) {
            throw new IllegalArgumentException("El fitxer seleccionat no existeix.");
        }

        if (!Files.isRegularFile(copia)) {
            throw new IllegalArgumentException("El fitxer seleccionat no és vàlid.");
        }

        try {
            Files.copy(copia, baseDades, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("No s'ha pogut restaurar la còpia de seguretat.", e);
        }
    }
}