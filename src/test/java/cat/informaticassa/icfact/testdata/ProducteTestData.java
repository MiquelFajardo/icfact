package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class ProducteTestData {

    private static final ProducteRepository repository = new ProducteRepository();
    private static final IvaRepository ivaRepository = new IvaRepository();

    private ProducteTestData() {
    }

    public static void carregar() {

        Iva iva21 = ivaRepository.buscarPerPercentatge(new BigDecimal("21.00")).orElseThrow();
        Iva iva10 = ivaRepository.buscarPerPercentatge(new BigDecimal("10.00")).orElseThrow();

        guardar(
                "P0001",
                "Manteniment informàtic",
                "Servei de manteniment informàtic.",
                iva21,
                new BigDecimal("35.00")
        );

        guardar(
                "P0002",
                "Instal·lació Windows",
                "Instal·lació del sistema operatiu.",
                iva21,
                new BigDecimal("60.00")
        );

        guardar(
                "P0003",
                "Allotjament web",
                "Servei anual d'allotjament.",
                iva10,
                new BigDecimal("90.00")
        );

    }

    private static void guardar(String codi,
                                String nom,
                                String descripcio,
                                Iva iva,
                                BigDecimal preu) {

        Producte producte = Producte.builder()
                .codi(codi)
                .nom(nom)
                .descripcio(descripcio)
                .iva(iva)
                .preu(preu)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();

        repository.guardar(producte);

    }
}