package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RecalcularFacturaService {

    private final RecalcularLiniaFacturaService recalcularLiniaService = new RecalcularLiniaFacturaService();

    public void executar(Factura factura) {
        if (factura == null) throw new IllegalArgumentException("La factura no pot ser nul·la.");

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (LiniaFactura linia : factura.getLinies()) {
            recalcularLiniaService.executar(linia);
            if (linia.getSubtotal() != null) subtotal = subtotal.add(linia.getSubtotal());
            if (linia.getTotal() != null) total = total.add(linia.getTotal());
        }

        BigDecimal iva = total.subtract(subtotal);

        factura.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        factura.setIva(iva.setScale(2, RoundingMode.HALF_UP));
        factura.setTotal(total.setScale(2, RoundingMode.HALF_UP));
    }
}