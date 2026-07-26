package cat.informaticassa.icfact.ui.util;

import cat.informaticassa.icfact.ui.util.dirty.RespostaDirty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.util.Optional;

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



    public static void error(String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    public static void error(String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    public static void informacio(String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    public static boolean confirmar(String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        return alert.showAndWait().filter(button -> button == ButtonType.OK).isPresent();
    }

    public static RespostaDirty confirmarCanvis(Window owner) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(owner);
        alert.setTitle("Canvis sense desar");
        alert.setHeaderText("Hi ha canvis sense desar.");
        alert.setContentText("Què vols fer?");
        ButtonType botoDesar = new ButtonType("💾 Desa");
        ButtonType botoDescartar = new ButtonType("🗑 Descarta");
        ButtonType botoCancelar = new ButtonType("✖ Cancel·la", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(botoDesar, botoDescartar, botoCancelar);
        Optional<ButtonType> resultat = alert.showAndWait();

        if (resultat.isPresent()) {
            if (resultat.get() == botoDesar) {
                return RespostaDirty.DESAR;
            }
            if (resultat.get() == botoDescartar) {
                return RespostaDirty.DESCARTAR;
            }
        }
        return RespostaDirty.CANCELAR;
    }
}