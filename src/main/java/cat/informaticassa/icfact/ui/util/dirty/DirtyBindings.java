package cat.informaticassa.icfact.ui.util.dirty;

import javafx.scene.Node;
import javafx.scene.control.*;

public final class DirtyBindings {

    private DirtyBindings() {
    }

    public static void registrar(DirtyTracker tracker, Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof TextField textField) {
                textField.textProperty().addListener((obs, oldValue, newValue) -> tracker.marcarModificat());
            }
            else if (node instanceof TextArea textArea) {
                textArea.textProperty().addListener((obs, oldValue, newValue) -> tracker.marcarModificat());
            }
            else if (node instanceof CheckBox checkBox) {
                checkBox.selectedProperty().addListener((obs, oldValue, newValue) -> tracker.marcarModificat());
            }
            else if (node instanceof ColorPicker colorPicker) {
                colorPicker.valueProperty().addListener((obs, oldValue, newValue) -> tracker.marcarModificat());
            }
            else if (node instanceof ComboBox<?> comboBox) {
                comboBox.valueProperty().addListener((obs, oldValue, newValue) -> tracker.marcarModificat());
            }
        }
    }
}