package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.exception.PressupostNullException;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RecalcularPressupostService {
    private static final BigDecimal CENT = BigDecimal.valueOf(100);

    public void executar(Pressupost pressupost) {
        if (pressupost == null) {
            throw new PressupostNullException("El pressupost no pot ser nul.");
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;

        for (LiniaPressupost linia : pressupost.getLinies()) {
            final BigDecimal importLinia = linia.getPreu()
                    .multiply(linia.getQuantitat());

            final BigDecimal descompte = importLinia
                    .multiply(linia.getDte())
                    .divide(CENT, 2, RoundingMode.HALF_UP);

            final BigDecimal subtotalLinia = importLinia.subtract(descompte);

            final BigDecimal ivaLinia = subtotalLinia
                    .multiply(linia.getIva().getPercentatge())
                    .divide(CENT, 2, RoundingMode.HALF_UP);

            linia.setSubtotal(subtotalLinia);
            linia.setTotal(subtotalLinia.add(ivaLinia));

            subtotal = subtotal.add(subtotalLinia);
            iva = iva.add(ivaLinia);
        }

        pressupost.setSubtotal(subtotal);
        pressupost.setIva(iva);
        pressupost.setTotal(subtotal.add(iva));
    }
}
