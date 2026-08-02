package cat.informaticassa.icfact.ui.components.producte;

import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.ui.components.FormDecimalField;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class ProductePane extends GridPane {
    private final TextField txtCodi = new TextField();
    private final TextField txtNom = new TextField();
    private final TextArea txtDescripcio = new TextArea();
    private final FormDecimalField txtPreu = new FormDecimalField();
    private final ComboBox<Iva> cmbIva = new ComboBox<>();
    private final Button botoNouIva = new Button("+");
    private final CheckBox chkActiu = new CheckBox("Article actiu");

    public ProductePane() {
        setHgap(15);
        setVgap(15);
        int fila = 0;

        add(new FormLabel("Codi"), 0, fila);
        add(txtCodi, 1, fila++);

        add(new FormLabel("Nom"), 0, fila);
        add(txtNom, 1, fila++);

        add(new FormLabel("Descripció"), 0, fila);
        add(txtDescripcio, 1, fila++);

        add(new FormLabel("Preu"), 0, fila);
        add(txtPreu, 1, fila++);

        add(new FormLabel("IVA"), 0, fila);
        HBox filaIva = new HBox(10);
        HBox.setHgrow(cmbIva, Priority.ALWAYS);
        filaIva.getChildren().addAll(cmbIva, botoNouIva);
        add(filaIva, 1, fila++);

        add(chkActiu, 1, fila);

        txtDescripcio.setPrefRowCount(4);
        txtDescripcio.setWrapText(true);

        txtCodi.setMaxWidth(Double.MAX_VALUE);
        txtNom.setMaxWidth(Double.MAX_VALUE);
        txtDescripcio.setMaxWidth(Double.MAX_VALUE);
        txtPreu.setMaxWidth(Double.MAX_VALUE);
        cmbIva.setMaxWidth(Double.MAX_VALUE);

        GridPane.setHgrow(txtCodi, Priority.ALWAYS);
        GridPane.setHgrow(txtNom, Priority.ALWAYS);
        GridPane.setHgrow(txtDescripcio, Priority.ALWAYS);
        GridPane.setHgrow(txtPreu, Priority.ALWAYS);
        GridPane.setHgrow(cmbIva, Priority.ALWAYS);

        mostrarCampActiu(false);
        new ProductePaneController(this);
    }

    public void mostrar(Producte producte) {
        txtCodi.setText(producte.getCodi());
        txtNom.setText(producte.getNom());
        txtDescripcio.setText(producte.getDescripcio());
        txtPreu.setValue(producte.getPreu());
        if (producte.getIva() != null) {
            cmbIva.getItems().stream()
                    .filter(i -> i.getId().equals(producte.getIva().getId()))
                    .findFirst()
                    .ifPresent(cmbIva::setValue);
        }
        chkActiu.setSelected(producte.isActiu());
    }

    public void mostrarCampActiu(boolean mostrar) {
        chkActiu.setVisible(mostrar);
        chkActiu.setManaged(mostrar);
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(
                tracker,
                txtCodi,
                txtNom,
                txtDescripcio,
                txtPreu,
                cmbIva,
                chkActiu
        );
    }
}