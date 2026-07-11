package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.repository.AdrecaRepository;
import cat.informaticassa.icfact.geografia.repository.PoblacioRepository;
import cat.informaticassa.icfact.geografia.repository.ProvinciaRepository;

public class AdrecaTestData {
    private static final AdrecaRepository adrecaRepository = new AdrecaRepository();
    private static final PoblacioRepository poblacioRepository = new PoblacioRepository();
    private static final ProvinciaRepository provinciaRepository = new ProvinciaRepository();

    private AdrecaTestData() {
    }

    public static void carregar() {
        Poblacio cassa = poblacioRepository.buscarPerNomIProvincia(
                "Cassà de la Selva",
                provinciaRepository.buscarPerCodi("GI").orElseThrow()
        ).orElseThrow();

        Poblacio llagostera = poblacioRepository.buscarPerNomIProvincia(
                "Llagostera",
                provinciaRepository.buscarPerCodi("GI").orElseThrow()
        ).orElseThrow();

        Poblacio barcelona = poblacioRepository.buscarPerNomIProvincia(
                "Barcelona",
                provinciaRepository.buscarPerCodi("B").orElseThrow()
        ).orElseThrow();

        guardar("Carrer Major", "1", "17244", cassa);
        guardar("Carrer Barcelona", "25", "17240", llagostera);
        guardar("Passeig de Gràcia", "100", "08001", barcelona);
    }

    private static void guardar(String carrer,
                                String numero,
                                String codiPostal,
                                Poblacio poblacio) {

        Adreca adreca = Adreca.builder()
                .carrer(carrer)
                .numero(numero)
                .codiPostal(codiPostal)
                .poblacio(poblacio)
                .build();

        adrecaRepository.guardar(adreca);
    }
}
