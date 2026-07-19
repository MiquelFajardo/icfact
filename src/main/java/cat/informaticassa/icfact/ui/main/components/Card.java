package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Card extends VBox {

    public Card(String titol, Node contingut) {
        setSpacing(15);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_LEFT);
        setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 12;
                -fx-border-color: transparent;
                -fx-border-radius: 12;
                """);
        Label lblTitol = new Label(titol);
        lblTitol.setFont(Tema.SUBTITOL);
        getChildren().addAll(lblTitol, contingut);
    }
}