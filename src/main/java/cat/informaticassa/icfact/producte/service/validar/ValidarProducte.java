package cat.informaticassa.icfact.producte.service.validar;

import cat.informaticassa.icfact.producte.model.Producte;

public class ValidarProducte {

    public void executar(Producte producte) {
        if (producte == null) throw new IllegalArgumentException("El producte no pot ser nul.");
        if (producte.getCodi() == null || producte.getCodi().isBlank()) throw new IllegalArgumentException("El codi és obligatori.");
        if (producte.getNom() == null || producte.getNom().isBlank()) throw new IllegalArgumentException("El nom és obligatori.");
        if (producte.getPreu() == null) throw new IllegalArgumentException("El preu és obligatori.");
        if (producte.getPreu().compareTo(java.math.BigDecimal.ZERO) < 0) throw new IllegalArgumentException("El preu no pot ser negatiu.");
        if (producte.getIva() == null) throw new IllegalArgumentException("L'IVA és obligatori.");
    }
}