package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.LiniaPressupostRepository;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.math.BigDecimal;

public final class LiniaPressupostTestData {

    private static final LiniaPressupostRepository repository = new LiniaPressupostRepository();
    private static final PressupostRepository pressupostRepository = new PressupostRepository();
    private static final ProducteRepository producteRepository = new ProducteRepository();
    private static final IvaRepository ivaRepository = new IvaRepository();

    private LiniaPressupostTestData() {
    }

    public static void carregar() {
        Pressupost pressupost = pressupostRepository
                .buscarPerNumero("P-2026-000001")
                .orElseThrow();
        Producte manteniment = producteRepository
                .buscarPerCodi("P0001")
                .orElseThrow();
        Producte windows = producteRepository
                .buscarPerCodi("P0002")
                .orElseThrow();
        Iva iva21 = ivaRepository
                .buscarPerPercentatge(new BigDecimal("21.00"))
                .orElseThrow();
        guardar(
                pressupost,
                manteniment,
                iva21,
                new BigDecimal("2"),
                new BigDecimal("35.00"),
                BigDecimal.ZERO,
                new BigDecimal("70.00"),
                new BigDecimal("84.70")
        );

        guardar(
                pressupost,
                windows,
                iva21,
                BigDecimal.ONE,
                new BigDecimal("60.00"),
                new BigDecimal("10.00"),
                new BigDecimal("54.00"),
                new BigDecimal("65.34")
        );
    }

    private static void guardar(Pressupost pressupost,
                                Producte producte,
                                Iva iva,
                                BigDecimal quantitat,
                                BigDecimal preu,
                                BigDecimal dte,
                                BigDecimal subtotal,
                                BigDecimal total) {

        LiniaPressupost linia = LiniaPressupost.builder()
                .pressupost(pressupost)
                .producte(producte)
                .descripcio(producte.getNom())
                .quantitat(quantitat)
                .preu(preu)
                .dte(dte)
                .iva(iva)
                .subtotal(subtotal)
                .total(total)
                .actiu(true)
                .build();

        repository.guardar(linia);

    }
}