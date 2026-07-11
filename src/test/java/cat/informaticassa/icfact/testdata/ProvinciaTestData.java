package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.repository.PaisRepository;
import cat.informaticassa.icfact.geografia.repository.ProvinciaRepository;

public final class ProvinciaTestData {

    private static final ProvinciaRepository provinciaRepository = new ProvinciaRepository();
    private static final PaisRepository paisRepository = new PaisRepository();

    private ProvinciaTestData() {
    }

    public static void carregar() {
        Pais espanya = paisRepository.buscarPerCodiIso("ES").orElseThrow();

        guardar("Girona", "GI", espanya);
        guardar("Barcelona", "B", espanya);

    }

    private static void guardar(String nom, String codi, Pais pais) {
        Provincia provincia = Provincia.builder()
                .nom(nom)
                .codi(codi)
                .pais(pais)
                .build();

        provinciaRepository.guardar(provincia);
    }
}
