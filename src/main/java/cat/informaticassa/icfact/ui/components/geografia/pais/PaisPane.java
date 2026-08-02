package cat.informaticassa.icfact.ui.components.geografia.pais;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.ui.components.forms.FormGrid;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class PaisPane extends FormGrid {

    private final TextField txtNom = new TextField();
    private final TextField txtCodiIso = new TextField();

    public PaisPane() {
        afegirCamp("Nom", txtNom);
        afegirCamp("Codi ISO", txtCodiIso);
    }

    public void setPais(Pais pais) {
        if (pais == null) {
            return;
        }
        txtNom.setText(pais.getNom());
        txtCodiIso.setText(pais.getCodiIso());
    }
}