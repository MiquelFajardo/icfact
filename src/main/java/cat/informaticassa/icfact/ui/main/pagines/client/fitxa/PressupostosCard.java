package cat.informaticassa.icfact.ui.main.pagines.client.fitxa;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class PressupostosCard extends BorderPane {
    private final Button botoNou = new Button("+ Nou");
    private final Button botoVeureTots = new Button("Veure tots");
    private final ListView<String> llista = new ListView<>();

    public PressupostosCard() {
        setPadding(new Insets(15));
        Label titol = new Label("Pressupostos");
        titol.getStyleClass().add("card-title");
        HBox superior = new HBox();
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        superior.getChildren().addAll(
                titol,
                spacer,
                botoNou
        );

        setTop(superior);
        llista.setPlaceholder(new Label("No hi ha pressupostos."));
        setCenter(llista);
        BorderPane.setMargin(llista, new Insets(10, 0, 10, 0));
        setBottom(botoVeureTots);
    }
}