package cat.informaticassa.icfact.ui.main.view;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    public MainView() {

        Label label = new Label("Benvingut a ICFact");

       label.setFont(Tema.TITOL);

        setCenter(label);
        BorderPane.setAlignment(label, Pos.CENTER);

    }

}