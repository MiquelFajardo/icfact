package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.repository.PoblacioRepository;
import cat.informaticassa.icfact.geografia.repository.ProvinciaRepository;

import java.util.TreeSet;

public class PoblacioTestData {
    private static final PoblacioRepository poblacioRepository = new PoblacioRepository();
    private static final ProvinciaRepository provinciaRepository = new ProvinciaRepository();

    private PoblacioTestData() {
    }

    public static void carregar() {
        Provincia girona = provinciaRepository.buscarPerCodi("GI").orElseThrow();
        Provincia barcelona = provinciaRepository.buscarPerCodi("B").orElseThrow();
        guardar("Cassà de la Selva", girona, "17244");
        guardar("Llagostera", girona, "17240");
        guardar("Barcelona", barcelona, "08001");
    }

    private static void guardar(String nom, Provincia provincia, String codiPostal) {
        Poblacio poblacio = Poblacio.builder()
                .nom(nom)
                .provincia(provincia)
                .codiPostal(new TreeSet<>())
                .build();
        poblacio.getCodiPostal().add(codiPostal);
        poblacioRepository.guardar(poblacio);
    }
}
