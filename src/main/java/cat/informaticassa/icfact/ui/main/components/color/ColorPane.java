package cat.informaticassa.icfact.ui.main.components.color;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public class ColorPane extends GridPane {
    private final ColorPicker colorPicker = new ColorPicker();

    public ColorPane() {
        setHgap(15);
        setVgap(15);
        setMaxWidth(Double.MAX_VALUE);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        getColumnConstraints().addAll(c1, c2);
        colorPicker.setPrefWidth(300);
        colorPicker.setMinWidth(300);
        colorPicker.setMaxWidth(300);
        add(new FormLabel("Color"), 0, 0);
        add(colorPicker, 1, 0);
    }

    public void mostrar(Empresa empresa) {
        if (empresa.getColor() == null || empresa.getColor().isBlank()) {
            return;
        }
        colorPicker.setValue(Color.web(empresa.getColor()));
    }

    public void actualitzar(Empresa empresa) {
        Color color = colorPicker.getValue();
        if (color == null) {
            empresa.setColor(null);
            return;
        }
        empresa.setColor(String.format(
                "#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)));
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(tracker, colorPicker);
    }
}