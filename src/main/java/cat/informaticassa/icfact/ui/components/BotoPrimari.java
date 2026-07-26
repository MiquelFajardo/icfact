package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.scene.control.Button;

public class BotoPrimari extends Button {
    public BotoPrimari(String text) {
        super(text);
        setPrefHeight(42);
        setMinWidth(130);
        setFocusTraversable(false);
        actualitzarColor();
        Tema.colorPrincipalProperty().addListener((o,a,n) -> actualitzarColor());
    }

    private void actualitzarColor() {
        setStyle("""
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-cursor: hand;
                """.formatted(toHex(Tema.getColorPrincipal())));
    }

    private String toHex(javafx.scene.paint.Color c) {
        return String.format("#%02X%02X%02X",
                (int)(c.getRed()*255),
                (int)(c.getGreen()*255),
                (int)(c.getBlue()*255));
    }
}