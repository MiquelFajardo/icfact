package cat.informaticassa.icfact.ui.components;

import javafx.scene.control.Button;

public class BotoSecundari extends Button {

    public BotoSecundari(String text) {
        super(text);
        setPrefHeight(42);
        setMinWidth(130);
        setFocusTraversable(false);
        destacar(false);
    }

    public void destacar(boolean destacar) {
        if (destacar) {
            setStyle("""
                    -fx-background-color:#DC2626;
                    -fx-text-fill:white;
                    -fx-background-radius:8;
                    -fx-border-radius:8;
                    -fx-font-size:14px;
                    -fx-font-weight:bold;
                    -fx-cursor:hand;
                    """);
        } else {
            setStyle("""
                    -fx-background-color:white;
                    -fx-text-fill:#1F2937;
                    -fx-border-color:#D1D5DB;
                    -fx-border-width:1;
                    -fx-background-radius:8;
                    -fx-border-radius:8;
                    -fx-font-size:14px;
                    -fx-font-weight:bold;
                    -fx-cursor:hand;
                    """);
        }
    }
}