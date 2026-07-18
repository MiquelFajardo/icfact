package cat.informaticassa.icfact.ui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

public final class Alerta {

    private Alerta() {
    }

    public static void error(Window owner, String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    public static void informacio(Window owner, String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    public static boolean confirmar(Window owner, String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(owner);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);

        return alert.showAndWait().filter(button -> button == ButtonType.OK).isPresent();
    }
}