package cat.informaticassa.icfact.infraestructura.service;

import java.time.Year;

public class GenerarNumeroDocumentService {
    private GenerarNumeroDocumentService() {
    }

    public static String generar(String prefix, long numero) {

        return String.format(
                "%s%d%06d",
                prefix,
                Year.now().getValue(),
                numero
        );
    }
}
