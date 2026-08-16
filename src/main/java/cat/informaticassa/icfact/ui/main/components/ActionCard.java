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

    private static final String ESTIL_NORMAL = """
            -fx-background-color: #16263D;
            -fx-background-radius: 12;
            -fx-border-color: #233A59;
            -fx-border-width: 1;
            -fx-border-radius: 12;
            -fx-cursor: hand;
            """;

    private static final String ESTIL_HOVER = """
            -fx-background-color: #1C3150;
            -fx-background-radius: 12;
            -fx-border-color: #3B82F6;
            -fx-border-width: 1;
            -fx-border-radius: 12;
            -fx-cursor: hand;
            -fx-effect: dropshadow(gaussian, rgba(59,130,246,0.18), 12, 0, 0, 3);
            """;

    public ActionCard(String titol, String icona) {

        setAlignment(Pos.CENTER);
        setSpacing(12);

        setPadding(new Insets(18));

        setPrefHeight(128);
        setMinHeight(128);
        setMaxHeight(128);

        setMaxWidth(Double.MAX_VALUE);

        setStyle(ESTIL_NORMAL);

        ImageView image = new ImageView(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/icons/" + icona
                                )
                        )
                )
        );

        image.setFitWidth(42);
        image.setFitHeight(42);
        image.setPreserveRatio(true);

        Label label = new Label(titol);
        label.setFont(Tema.ETIQUETA);
        label.setStyle("""
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-text-fill: white;
                """);

        getChildren().addAll(
                image,
                label
        );

        setOnMouseEntered(e ->
                setStyle(ESTIL_HOVER)
        );

        setOnMouseExited(e ->
                setStyle(ESTIL_NORMAL)
        );
    }
}