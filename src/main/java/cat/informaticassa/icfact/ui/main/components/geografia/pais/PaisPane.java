package cat.informaticassa.icfact.ui.main.components.geografia.pais;

import cat.informaticassa.icfact.ui.main.components.FormLabel;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class PaisPane extends GridPane {
    private final TextField txtNom = new TextField();
    private final TextField txtCodiIso = new TextField();

    public PaisPane() {
        setHgap(15);
        setVgap(15);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(c1, c2);
        txtNom.setMaxWidth(Double.MAX_VALUE);
        txtCodiIso.setPrefWidth(80);
        add(new FormLabel("Nom"),0,0);
        add(txtNom,1,0);
        add(new FormLabel("Codi ISO"),0,1);
        add(txtCodiIso,1,1);
    }
}