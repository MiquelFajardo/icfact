package cat.informaticassa.icfact.ui.components.tasca;

import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class TascaPane extends GridPane {
    private final TextField txtTitol = new TextField();
    private final DatePicker dpDataLimit = new DatePicker();

    public TascaPane() {
        setHgap(15);
        setVgap(15);
        setPadding(new Insets(20));

        add(new FormLabel("Títol"), 0, 0);
        add(txtTitol, 1, 0);

        add(new FormLabel("Data límit"), 0, 1);
        add(dpDataLimit, 1, 1);

        txtTitol.setMaxWidth(Double.MAX_VALUE);
        dpDataLimit.setMaxWidth(Double.MAX_VALUE);

        GridPane.setHgrow(txtTitol, Priority.ALWAYS);
        GridPane.setHgrow(dpDataLimit, Priority.ALWAYS);
    }

    public void mostrar(Tasca tasca) {
        txtTitol.setText(tasca.getTitol() == null ? "" : tasca.getTitol());
        dpDataLimit.setValue(tasca.getDataLimit());
    }

    public void guardar(Tasca tasca) {
        tasca.setTitol(txtTitol.getText().trim());
        tasca.setDataLimit(dpDataLimit.getValue());
    }

    public TextField getTxtTitol() {
        return txtTitol;
    }

    public DatePicker getDpDataLimit() {
        return dpDataLimit;
    }
}