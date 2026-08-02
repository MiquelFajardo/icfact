package cat.informaticassa.icfact.ui.components.iva;

import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.ui.components.FormDecimalField;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class IvaPane extends GridPane {
    private final TextField txtNom = new TextField();
    private final FormDecimalField txtPercentatge = new FormDecimalField();
    private final CheckBox chkActiu = new CheckBox("IVA actiu");

    public IvaPane() {
        setHgap(15);
        setVgap(15);
        int fila = 0;
        add(new FormLabel("Nom"), 0, fila);
        add(txtNom, 1, fila++);
        add(new FormLabel("Percentatge"), 0, fila);
        add(txtPercentatge, 1, fila++);
        add(chkActiu, 1, fila);
        txtNom.setMaxWidth(Double.MAX_VALUE);
        txtPercentatge.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(txtNom, Priority.ALWAYS);
        GridPane.setHgrow(txtPercentatge, Priority.ALWAYS);
        mostrarCampActiu(false);
    }

    public void mostrar(Iva iva) {
        txtNom.setText(iva.getNom());
        if (iva.getPercentatge() != null) {
            txtPercentatge.setValue(iva.getPercentatge());
        }
        chkActiu.setSelected(iva.isActiu());
    }

    public void mostrarCampActiu(boolean mostrar) {
        chkActiu.setVisible(mostrar);
        chkActiu.setManaged(mostrar);
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(
                tracker,
                txtNom,
                txtPercentatge,
                chkActiu
        );
    }
}