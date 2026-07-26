package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class ActionCard extends VBox {

    public ActionCard(String titol, String icona) {

        setAlignment(Pos.CENTER);
        setSpacing(15);

        setPadding(new Insets(20));

        setPrefSize(250, 150);

        setStyle("""
                -fx-background-color: #c6c8cc;
                -fx-background-radius: 12;
                -fx-border-color: #7d7e80;
                -fx-border-radius: 12;
                -fx-cursor: hand;
                """);

        ImageView image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/" + icona))));
        image.setFitWidth(42);
        image.setFitHeight(42);
        image.setPreserveRatio(true);

        Label label = new Label(titol);
        label.setFont(Tema.SUBTITOL);

        getChildren().addAll(image, label);

        setOnMouseEntered(e ->
                setStyle("""
                        -fx-background-color: #EAF2FF;
                        -fx-background-radius:12;
                        -fx-border-color:#3B82F6;
                        -fx-border-radius:12;
                        -fx-cursor:hand;
                        """));

        setOnMouseExited(e ->
                setStyle("""
                        -fx-background-color:#c6c8cc;
                        -fx-background-radius:12;
                        -fx-border-color:#7d7e80;
                        -fx-border-radius:12;
                        -fx-cursor:hand;
                        """));
    }

    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> action.run());
    }
}