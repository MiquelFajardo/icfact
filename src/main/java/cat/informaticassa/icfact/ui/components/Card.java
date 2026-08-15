package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class Card extends VBox {

    public Card() {
        inicialitzar();
    }

    public Card(String titol, Node contingut) {
        inicialitzar();

        if (titol != null && !titol.isBlank()) {
            Label label = new Label(titol);
            label.setFont(Tema.TITOL_CARD);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setStyle("""
                -fx-font-weight: bold;
                -fx-text-fill: #111827;
                """);
            getChildren().add(label);
        }

        if (contingut != null) {
            getChildren().add(contingut);
        }
    }

    private void inicialitzar() {
        setAlignment(Pos.TOP_CENTER);
        setFillWidth(true);
        setSpacing(Tema.CARD_SPACING);
        setPadding(Tema.CARD_PADDING);
        setMaxWidth(Double.MAX_VALUE);
        setPrefWidth(Double.MAX_VALUE);

        setBackground(new Background(
                new BackgroundFill(
                        Tema.CARD_BACKGROUND,
                        new CornerRadii(Tema.CARD_RADIUS),
                        Insets.EMPTY)));

        setBorder(new Border(
                new BorderStroke(
                        Tema.BORDER,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(Tema.CARD_RADIUS),
                        new BorderWidths(1))));
    }

    public void add(Node node) {
        getChildren().add(node);
    }

    public Card(String titol) {        inicialitzar();

        if (titol != null && !titol.isBlank()) {
            Label label = new Label(titol);
            label.setFont(Tema.TITOL_CARD);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setStyle("""
            -fx-font-weight: bold;
            -fx-text-fill: #111827;
            """);
            getChildren().add(label);
        }
    }

    public void afegir(Node node) {
        getChildren().add(node);
    }
}