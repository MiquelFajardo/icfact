package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.scene.control.Label;

public class FormLabel extends Label {
    public FormLabel(String text) {
        super(text);
        setMinWidth(150);
        setFont(Tema.ETIQUETA);
        setStyle("""
                -fx-font-weight: 600;
                -fx-text-fill: #374151;
                """);
    }
}