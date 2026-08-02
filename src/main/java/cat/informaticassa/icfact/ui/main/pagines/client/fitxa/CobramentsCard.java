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
public class CobramentsCard extends BorderPane {
    private final Button botoVeureTots = new Button("Veure tots");
    private final ListView<String> llista = new ListView<>();
    private final Label lblTotalPendent = new Label("0,00 €");

    public CobramentsCard() {
        setPadding(new Insets(15));
        Label titol = new Label("Cobraments pendents");
        titol.getStyleClass().add("card-title");
        setTop(titol);
        llista.setPlaceholder(new Label("No hi ha cobraments pendents."));
        setCenter(llista);
        BorderPane.setMargin(llista, new Insets(10, 0, 10, 0));
        HBox inferior = new HBox();
        Label lblText = new Label("Total pendent:");
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        inferior.getChildren().addAll(
                lblText,
                lblTotalPendent,
                spacer,
                botoVeureTots
        );
        setBottom(inferior);
    }

    public void actualitzarTotal(String total) {
        lblTotalPendent.setText(total);
    }
}