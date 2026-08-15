package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.model.LiniaFactura;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RecalcularLiniaFacturaService {
    private static final BigDecimal CENT = BigDecimal.valueOf(100);

    public void executar(LiniaFactura linia) {
        if (linia == null) {
            throw new IllegalArgumentException("La línia de factura no pot ser nul·la.");
        }
        BigDecimal importLinia = linia.getPreu().multiply(linia.getQuantitat());
        BigDecimal descompte = importLinia.multiply(linia.getDte()).divide(CENT, 2, RoundingMode.HALF_UP);
        BigDecimal subtotal = importLinia.subtract(descompte);
        BigDecimal iva = subtotal.multiply(linia.getIva().getPercentatge()).divide(CENT, 2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(iva);
        linia.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        linia.setTotal(total.setScale(2, RoundingMode.HALF_UP));
    }
}