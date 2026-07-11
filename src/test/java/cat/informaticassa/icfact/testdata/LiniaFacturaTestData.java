package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.factura.repository.LiniaFacturaRepository;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;

import java.math.BigDecimal;

public final class LiniaFacturaTestData {

    private static final LiniaFacturaRepository repository = new LiniaFacturaRepository();
    private static final FacturaRepository facturaRepository = new FacturaRepository();
    private static final ProducteRepository producteRepository = new ProducteRepository();
    private static final IvaRepository ivaRepository = new IvaRepository();

    private LiniaFacturaTestData() {
    }

    public static void carregar() {
        Factura factura = facturaRepository
                .buscarPerNumero("F-2026-000001")
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
                factura,
                manteniment,
                iva21,
                new BigDecimal("2"),
                new BigDecimal("35.00"),
                BigDecimal.ZERO,
                new BigDecimal("70.00"),
                new BigDecimal("84.70")
        );

        guardar(
                factura,
                windows,
                iva21,
                BigDecimal.ONE,
                new BigDecimal("60.00"),
                new BigDecimal("10.00"),
                new BigDecimal("54.00"),
                new BigDecimal("65.34")
        );

    }

    private static void guardar(Factura factura,
                                Producte producte,
                                Iva iva,
                                BigDecimal quantitat,
                                BigDecimal preu,
                                BigDecimal dte,
                                BigDecimal subtotal,
                                BigDecimal total) {

        LiniaFactura linia = LiniaFactura.builder()
                .factura(factura)
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