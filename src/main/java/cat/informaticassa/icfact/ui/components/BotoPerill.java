package cat.informaticassa.icfact.ui.components;

import javafx.scene.paint.Color;

public class BotoPerill extends BotoSecundari {

    public BotoPerill(String text) {
        super(text);

        setTextFill(Color.WHITE);

        setStyle("""
            -fx-background-color: #DC2626;
            -fx-background-radius: 8;
            -fx-border-radius: 8;
            -fx-border-color: #B91C1C;
            -fx-border-width: 1;
            """);

        hoverProperty().addListener((obs, oldValue, hover) -> {

            if (hover) {
                setStyle("""
                    -fx-background-color: #B91C1C;
                    -fx-background-radius: 8;
                    -fx-border-radius: 8;
                    -fx-border-color: #991B1B;
                    -fx-border-width: 1;
                    """);
            } else {
                setStyle("""
                    -fx-background-color: #DC2626;
                    -fx-background-radius: 8;
                    -fx-border-radius: 8;
                    -fx-border-color: #B91C1C;
                    -fx-border-width: 1;
                    """);
            }
        });
    }
}