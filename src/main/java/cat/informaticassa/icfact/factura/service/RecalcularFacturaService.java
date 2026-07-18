package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RecalcularFacturaService {

    public void executar(Factura factura) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;

        for (LiniaFactura linia : factura.getLinies()) {
            final BigDecimal importLinia = linia.getPreu()
                    .multiply(linia.getQuantitat());

            final BigDecimal descompte = importLinia
                    .multiply(linia.getDte())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            final BigDecimal subtotalLinia = importLinia.subtract(descompte);

            final BigDecimal ivaLinia = subtotalLinia
                    .multiply(linia.getIva().getPercentatge())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            linia.setSubtotal(subtotalLinia);
            linia.setTotal(subtotalLinia.add(ivaLinia));

            subtotal = subtotal.add(subtotalLinia);
            iva = iva.add(ivaLinia);
        }

        factura.setSubtotal(subtotal);
        factura.setIva(iva);
        factura.setTotal(subtotal.add(iva));
    }
}