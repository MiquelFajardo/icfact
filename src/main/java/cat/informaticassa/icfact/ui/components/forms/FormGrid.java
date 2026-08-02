package cat.informaticassa.icfact.ui.components.forms;

import cat.informaticassa.icfact.ui.main.components.FormLabel;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class FormGrid extends GridPane {
    private int fila = 0;

    public FormGrid() {
        setHgap(15);
        setVgap(15);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(c1, c2);
    }

    protected void afegirCamp(String etiqueta, Node control) {
        add(new FormLabel(etiqueta), 0, fila);
        GridPane.setHgrow(control, Priority.ALWAYS);
        add(control, 1, fila);
        fila++;
    }
}