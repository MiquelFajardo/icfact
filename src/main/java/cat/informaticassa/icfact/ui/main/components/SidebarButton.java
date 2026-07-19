package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SidebarButton extends Button {
    public SidebarButton(String text, String icona) {
        ImageView image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/" + icona))));

        image.setFitWidth(22);
        image.setFitHeight(22);
        image.setPreserveRatio(true);

        setGraphic(image);
        setText(text);

        setContentDisplay(ContentDisplay.LEFT);
        setGraphicTextGap(12);

        setAlignment(Pos.CENTER_LEFT);

        setPrefHeight(46);
        setMaxWidth(Double.MAX_VALUE);

        setPadding(new Insets(0, 15, 0, 15));

        setFont(Tema.TEXT_NORMAL);

        setFocusTraversable(false);

        setTooltip(new Tooltip(text));

        aplicarNormal();

        setOnMouseEntered(e -> aplicarHover());
        setOnMouseExited(e -> aplicarNormal());
    }

    private void aplicarNormal() {
        setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-background-radius: 8;
                -fx-cursor: hand;
                """);
    }

    private void aplicarHover() {
        setStyle("""
                -fx-background-color: rgba(255,255,255,0.12);
                -fx-text-fill: white;
                -fx-background-radius: 8;
                -fx-cursor: hand;
                """);
    }

    public void seleccionar(boolean seleccionat) {

        if (seleccionat) {

            setStyle("""
                    -fx-background-color: rgba(255,255,255,0.22);
                    -fx-text-fill: white;
                    -fx-background-radius: 8;
                    -fx-cursor: hand;
                    """);

        } else {

            aplicarNormal();

        }
    }
}