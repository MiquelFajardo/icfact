package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Card extends VBox {

    public Card() {
        inicialitzar();
    }

    public Card(String titol, Node contingut) {
        inicialitzar();

        afegirTitol(titol);

        if (contingut != null) {
            getChildren().add(contingut);
        }
    }

    public Card(String titol) {
        inicialitzar();
        afegirTitol(titol);
    }

    private void inicialitzar() {
        setAlignment(Pos.TOP_CENTER);
        setFillWidth(true);
        setSpacing(18);
        setPadding(new Insets(24));
        setMaxWidth(Double.MAX_VALUE);
        setPrefWidth(Double.MAX_VALUE);

        setBackground(new Background(
                new BackgroundFill(
                        Tema.CARD_BACKGROUND,
                        new CornerRadii(14),
                        Insets.EMPTY
                )
        ));

        setBorder(new Border(
                new BorderStroke(
                        Color.web("#E2E8F0"),
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(14),
                        new BorderWidths(1)
                )
        ));

        DropShadow ombra = new DropShadow();
        ombra.setRadius(12);
        ombra.setOffsetY(3);
        ombra.setColor(Color.rgb(15, 23, 42, 0.08));

        setEffect(ombra);
    }

    private void afegirTitol(String titol) {
        if (titol == null || titol.isBlank()) {
            return;
        }

        Label label = new Label(titol);
        label.setFont(Tema.TITOL_CARD);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER_LEFT);

        label.setStyle("""
                -fx-font-weight: bold;
                -fx-text-fill: #172033;
                """);

        getChildren().add(label);
    }

    public void add(Node node) {
        getChildren().add(node);
    }

    public void afegir(Node node) {
        getChildren().add(node);
    }

}