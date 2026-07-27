package cat.informaticassa.icfact.ui.main.components.geografia.provincia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class ProvinciaPane extends GridPane {
    private final ComboBox<Pais> cmbPais = new ComboBox<>();
    private final TextField txtNom = new TextField();
    private final TextField txtCodi = new TextField();

    public ProvinciaPane() {
        setHgap(15);
        setVgap(15);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(c1, c2);
        cmbPais.setMaxWidth(Double.MAX_VALUE);
        cmbPais.setDisable(true);
        txtNom.setMaxWidth(Double.MAX_VALUE);
        txtCodi.setMaxWidth(Double.MAX_VALUE);
        int fila = 0;
        add(new FormLabel("País"), 0, fila);
        add(cmbPais, 1, fila++);
        add(new FormLabel("Nom"), 0, fila);
        add(txtNom, 1, fila++);
        add(new FormLabel("Codi"), 0, fila);
        add(txtCodi, 1, fila);
    }
}