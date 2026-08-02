package cat.informaticassa.icfact.ui.components.formaPagament;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class FormaPagamentPane extends GridPane {
    private final TextField txtNom = new TextField();
    private final TextArea txtDescripcio = new TextArea();
    private final CheckBox chkMostrarIban = new CheckBox("Mostrar IBAN");
    private final CheckBox chkActiu = new CheckBox("Forma de pagament activa");

    public FormaPagamentPane() {
        setHgap(15);
        setVgap(15);
        int fila = 0;
        add(new FormLabel("Nom"), 0, fila);
        add(txtNom, 1, fila++);

        add(new FormLabel("Descripció"), 0, fila);
        add(txtDescripcio, 1, fila++);

        add(chkMostrarIban, 1, fila++);
        add(chkActiu, 1, fila);

        txtNom.setMaxWidth(Double.MAX_VALUE);
        txtDescripcio.setMaxWidth(Double.MAX_VALUE);

        txtDescripcio.setPrefRowCount(4);
        txtDescripcio.setWrapText(true);

        GridPane.setHgrow(txtNom, Priority.ALWAYS);
        GridPane.setHgrow(txtDescripcio, Priority.ALWAYS);

        mostrarCampActiu(false);
    }

    public void mostrar(FormaPagament formaPagament) {
        txtNom.setText(formaPagament.getNom());
        txtDescripcio.setText(formaPagament.getDescripcio());
        chkMostrarIban.setSelected(Boolean.TRUE.equals(formaPagament.getMostrarIban()));
        chkActiu.setSelected(formaPagament.isActiu());
    }

    public void mostrarCampActiu(boolean mostrar) {
        chkActiu.setVisible(mostrar);
        chkActiu.setManaged(mostrar);
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(
                tracker,
                txtNom,
                txtDescripcio,
                chkMostrarIban,
                chkActiu
        );
    }
}