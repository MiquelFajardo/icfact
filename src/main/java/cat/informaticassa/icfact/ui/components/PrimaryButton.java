package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class PrimaryButton extends Button {

    public PrimaryButton(String text) {
        super(text);
        setCursor(Cursor.HAND);
        setFont(javafx.scene.text.Font.font("Inter", FontWeight.BOLD, 14));
        setTextFill(Color.WHITE);
        setPrefHeight(48);
        setMinHeight(48);
        setMaxWidth(Double.MAX_VALUE);
        aplicarEstil(Tema.getColorPrincipal());

        /*
         * Hover
         */
        setOnMouseEntered(e -> aplicarEstil(Tema.getColorPrincipal().brighter()));
        setOnMouseExited(e -> aplicarEstil(Tema.getColorPrincipal()));

        /*
         * Click
         */
        setOnMousePressed(e -> aplicarEstil(Tema.getColorPrincipal().darker()));
        setOnMouseReleased(e -> aplicarEstil(Tema.getColorPrincipal()));
    }

    private void aplicarEstil(Color color) {

        setBackground(new Background(new BackgroundFill(color, new CornerRadii(Tema.BORDER_RADIUS), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(Tema.BORDER_RADIUS), BorderWidths.DEFAULT)));
    }

}