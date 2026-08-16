package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.exception.PressupostNullException;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RecalcularPressupostService {
    private final RecalcularLiniaPressupostService recalcularLiniaService = new RecalcularLiniaPressupostService();

    public void executar(Pressupost pressupost) {
        if (pressupost == null) throw new PressupostNullException("El pressupost no pot ser nul.");
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (LiniaPressupost linia : pressupost.getLinies()) {
            recalcularLiniaService.executar(linia);
            if (linia.getSubtotal() != null) subtotal = subtotal.add(linia.getSubtotal());
            if (linia.getTotal() != null) total = total.add(linia.getTotal());
        }
        BigDecimal iva = total.subtract(subtotal);
        pressupost.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        pressupost.setIva(iva.setScale(2, RoundingMode.HALF_UP));
        pressupost.setTotal(total.setScale(2, RoundingMode.HALF_UP));
    }
}