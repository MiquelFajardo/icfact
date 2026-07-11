package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.repository.PaisRepository;

public class PaisTestData {
    private static final PaisRepository repository = new PaisRepository();

    private PaisTestData() {
    }

    public static void carregar() {
        repository.guardar(
                Pais.builder()
                        .nom("Espanya")
                        .codiIso("ES")
                        .build()
        );

        repository.guardar(
                Pais.builder()
                        .nom("França")
                        .codiIso("FR")
                        .build()
        );

        repository.guardar(
                Pais.builder()
                        .nom("Portugal")
                        .codiIso("PT")
                        .build()
        );

    }
}
