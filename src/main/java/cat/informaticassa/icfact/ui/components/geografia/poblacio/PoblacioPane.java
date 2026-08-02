package cat.informaticassa.icfact.ui.components.geografia.poblacio;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.components.forms.FormGrid;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class PoblacioPane extends FormGrid {
    private final ComboBox<Provincia> cmbProvincia = new ComboBox<>();
    private final TextField txtNom = new TextField();
    private final TextField txtCodiPostal = new TextField();

    public PoblacioPane() {
        cmbProvincia.setDisable(true);
        afegirCamp("Província", cmbProvincia);
        afegirCamp("Nom", txtNom);
        afegirCamp("Codi postal", txtCodiPostal);
    }

    public void setProvincia(Provincia provincia) {
        cmbProvincia.getItems().clear();
        cmbProvincia.getItems().add(provincia);
        cmbProvincia.getSelectionModel().select(0);
    }

    public void setPoblacio(Poblacio poblacio) {
        if (poblacio == null) {
            return;
        }
        setProvincia(poblacio.getProvincia());
        txtNom.setText(poblacio.getNom());
        txtCodiPostal.setText(
                poblacio.getCodiPostal().isEmpty()
                        ? ""
                        : poblacio.getCodiPostal().iterator().next()
        );
    }
}