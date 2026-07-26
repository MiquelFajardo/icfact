package cat.informaticassa.icfact.ui.main.components.geografia;

import cat.informaticassa.icfact.geografia.model.Pais;

public class PaisBinder {
    private final PaisPane pane;

    public PaisBinder(PaisPane pane) {
        this.pane = pane;
    }

    public void carregar(Pais pais) {
        pane.getTxtNom().setText(pais.getNom());
        pane.getTxtCodiIso().setText(pais.getCodiIso());
    }

    public void actualitzar(Pais pais) {
        pais.setNom(pane.getTxtNom().getText());
        pais.setCodiIso(pane.getTxtCodiIso().getText());
    }
}