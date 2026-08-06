package cat.informaticassa.icfact.ui.components.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class ProducteNoExisteixDialog extends Dialog<Boolean> {
    private boolean crearProducte;

    public ProducteNoExisteixDialog(String nom) {
        setTitle("Producte no existent");
        ButtonType acceptar = new ButtonType("Acceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelar = new ButtonType("Cancel·lar", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(acceptar, cancelar);
        RadioButton rbCrear = new RadioButton("Crear un producte nou");
        RadioButton rbText = new RadioButton("Utilitzar aquest text només en aquest pressupost");
        ToggleGroup grup = new ToggleGroup();
        rbCrear.setToggleGroup(grup);
        rbText.setToggleGroup(grup);
        rbCrear.setSelected(true);
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));
        box.getChildren().addAll(
                new Label("El producte següent no existeix:"),
                new Label("\"" + nom + "\""),
                new Separator(),
                rbCrear,
                rbText
        );

        getDialogPane().setContent(box);
        setResultConverter(button -> {
            if (button != acceptar) {
                return false;
            }
            crearProducte = rbCrear.isSelected();
            return true;
        });
    }
}