package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RecalcularLiniaPressupostService {
    public void executar(LiniaPressupost linia) {
        BigDecimal quantitat = valor(linia.getQuantitat());
        BigDecimal preu = valor(linia.getPreu());
        BigDecimal descompte = valor(linia.getDte());
        BigDecimal percentatgeIva = BigDecimal.ZERO;

        if (linia.getIva() != null) {
            percentatgeIva = valor(linia.getIva().getPercentatge());
        }

        BigDecimal subtotal = quantitat.multiply(preu);
        if (descompte.compareTo(BigDecimal.ZERO) > 0) {
            subtotal = subtotal.subtract(
                    subtotal.multiply(descompte)
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
            );
        }

        BigDecimal iva = subtotal.multiply(percentatgeIva).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(iva);
        linia.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        linia.setTotal(total.setScale(2, RoundingMode.HALF_UP));
    }

    private BigDecimal valor(BigDecimal valor) {
        return valor == null
                ? BigDecimal.ZERO
                : valor;
    }
}