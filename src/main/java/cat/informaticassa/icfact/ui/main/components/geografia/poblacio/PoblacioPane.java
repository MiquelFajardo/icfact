package cat.informaticassa.icfact.ui.main.components.geografia.poblacio;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class PoblacioPane extends GridPane {

    private final ComboBox<Provincia> cmbProvincia = new ComboBox<>();

    private final TextField txtNom = new TextField();
    private final TextField txtCodiPostal = new TextField();

    public PoblacioPane() {
        setHgap(15);
        setVgap(15);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(c1, c2);
        cmbProvincia.setMaxWidth(Double.MAX_VALUE);
        cmbProvincia.setDisable(true);
        txtNom.setMaxWidth(Double.MAX_VALUE);
        txtCodiPostal.setMaxWidth(Double.MAX_VALUE);
        int fila = 0;
        add(new FormLabel("Província"), 0, fila);
        add(cmbProvincia, 1, fila++);
        add(new FormLabel("Nom"), 0, fila);
        add(txtNom, 1, fila++);
        add(new FormLabel("Codi postal"), 0, fila);
        add(txtCodiPostal, 1, fila);
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