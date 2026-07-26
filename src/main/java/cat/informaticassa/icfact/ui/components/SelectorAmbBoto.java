package cat.informaticassa.icfact.ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import lombok.Getter;

@Getter
public class SelectorAmbBoto<T> extends HBox {
    private final ComboBox<T> combo = new ComboBox<>();
    private final Button botoNou = new Button("+");

    public SelectorAmbBoto() {
        setSpacing(8);
        setAlignment(Pos.CENTER_LEFT);
        combo.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(combo, javafx.scene.layout.Priority.ALWAYS);
        botoNou.setFocusTraversable(false);
        botoNou.setPrefSize(32, 32);
        botoNou.setStyle("""
            -fx-background-color:#F3F4F6;
            -fx-border-color:#D1D5DB;
            -fx-border-radius:8;
            -fx-background-radius:8;
            -fx-font-size:16;
            -fx-font-weight:bold;
            -fx-cursor:hand;
        """);
        getChildren().addAll(combo, botoNou);
    }

    public void setOnNou(Runnable runnable) {
        botoNou.setOnAction(e -> runnable.run());
    }
}