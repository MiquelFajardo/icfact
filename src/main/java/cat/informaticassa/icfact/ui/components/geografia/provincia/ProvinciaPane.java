package cat.informaticassa.icfact.ui.components.geografia.provincia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.components.forms.FormGrid;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class ProvinciaPane extends FormGrid {
    private final ComboBox<Pais> cmbPais = new ComboBox<>();
    private final TextField txtNom = new TextField();
    private final TextField txtCodi = new TextField();

    public ProvinciaPane() {
        cmbPais.setDisable(true);
        afegirCamp("País", cmbPais);
        afegirCamp("Nom", txtNom);
        afegirCamp("Codi", txtCodi);
    }

    public void setPais(Pais pais) {
        cmbPais.getItems().clear();
        cmbPais.getItems().add(pais);
        cmbPais.getSelectionModel().select(0);
    }

    public void setProvincia(Provincia provincia) {
        if (provincia == null) {
            return;
        }
        setPais(provincia.getPais());
        txtNom.setText(provincia.getNom());
        txtCodi.setText(provincia.getCodi());
    }
}