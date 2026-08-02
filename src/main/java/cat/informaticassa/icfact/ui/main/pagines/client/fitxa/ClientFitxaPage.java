package cat.informaticassa.icfact.ui.main.pagines.client.fitxa;

import javafx.geometry.Insets;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class ClientFitxaPage extends BorderPane {
    private final ClientFitxaHeader header = new ClientFitxaHeader();
    private final ClientCard clientCard = new ClientCard();
    private final PressupostosCard pressupostosCard = new PressupostosCard();
    private final FacturesCard facturesCard = new FacturesCard();
    private final CobramentsCard cobramentsCard = new CobramentsCard();

    public ClientFitxaPage() {
        setTop(header);
        VBox esquerra = new VBox();
        esquerra.setPadding(new Insets(20));
        esquerra.getChildren().add(clientCard);
        VBox dreta = new VBox(20);
        dreta.setPadding(new Insets(20));
        VBox.setVgrow(pressupostosCard, Priority.ALWAYS);
        VBox.setVgrow(facturesCard, Priority.ALWAYS);
        VBox.setVgrow(cobramentsCard, Priority.ALWAYS);
        dreta.getChildren().addAll(
                pressupostosCard,
                facturesCard,
                cobramentsCard
        );

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(
                esquerra,
                dreta
        );

        splitPane.setDividerPositions(0.30);

        setCenter(splitPane);
        new ClientFitxaEvents(this);

    }
}