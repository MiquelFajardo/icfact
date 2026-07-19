package cat.informaticassa.icfact.ui.main.components;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class WorkArea extends StackPane {

    public WorkArea() {
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public void mostrar(Node pagina) {
        getChildren().setAll(pagina);
    }
}