package cat.informaticassa.icfact.ui.components.iva;

import cat.informaticassa.icfact.iva.model.Iva;

public class IvaBinder {
    private final IvaPane formulari;

    public IvaBinder(IvaPane formulari) {
        this.formulari = formulari;
    }

    public void actualitzar(Iva iva) {
        iva.setNom(formulari.getTxtNom().getText().trim());
        iva.setPercentatge(formulari.getTxtPercentatge().getValue());
        iva.setActiu(formulari.getChkActiu().isSelected());
    }
}