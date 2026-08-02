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
public class FacturesCard extends BorderPane {
    private final Button botoNova = new Button("+ Nova");
    private final Button botoVeureTotes = new Button("Veure totes");
    private final ListView<String> llista = new ListView<>();

    public FacturesCard() {
        setPadding(new Insets(15));
        Label titol = new Label("Factures");
        titol.getStyleClass().add("card-title");
        HBox superior = new HBox();
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        superior.getChildren().addAll(
                titol,
                spacer,
                botoNova
        );

        setTop(superior);
        llista.setPlaceholder(new Label("No hi ha factures."));
        setCenter(llista);
        BorderPane.setMargin(llista, new Insets(10, 0, 10, 0));
        setBottom(botoVeureTotes);
    }
}