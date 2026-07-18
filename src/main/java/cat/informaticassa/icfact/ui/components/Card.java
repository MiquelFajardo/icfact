package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class Card extends VBox {

    public Card() {
        setAlignment(Pos.TOP_CENTER);
        setFillWidth(true);
        setSpacing(Tema.CARD_SPACING);
        setPadding(Tema.CARD_PADDING);
        setPrefWidth(Tema.CARD_WIDTH);
        setMaxWidth(Tema.CARD_WIDTH);
        setBackground(new Background(new BackgroundFill(Tema.CARD_BACKGROUND, new CornerRadii(Tema.CARD_RADIUS), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Tema.BORDER, BorderStrokeStyle.SOLID, new CornerRadii(Tema.CARD_RADIUS), new BorderWidths(1))));
    }

    public void add(Node node) {
        getChildren().add(node);
    }
}