package cat.informaticassa.icfact.ui.main.pagines.client.controller;

import cat.informaticassa.icfact.ui.components.dialogs.ClientDialog;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ClientEvents {
    private final ClientController controller;
    private final PauseTransition pausaBuscar = new PauseTransition(Duration.millis(300));

    public ClientEvents(ClientController controller) {
        this.controller = controller;
        inicialitzar();
    }

    private void inicialitzar() {
        controller.getPagina().getToolbar().getChkActius().setOnAction(e -> filtrar());
        controller.getPagina().getToolbar().getChkInactius().setOnAction(e -> filtrar());

        controller.getPagina().getToolbar().getTxtBuscar().textProperty().addListener(
                (obs, oldValue, newValue) -> {
                    pausaBuscar.stop();
                    pausaBuscar.setOnFinished(e -> buscar(newValue));
                    pausaBuscar.playFromStart();
                }
        );

        controller.getPagina().getToolbar().getBotoNou().setOnAction(e -> {
            ClientDialog dialog = new ClientDialog();
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
        });

        controller.getPagina().getTaula().setOnModificar(client -> {
            ClientDialog dialog = new ClientDialog(client);
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
        });

        controller.getPagina().getTaula().setOnPressupostos(client -> {
            // TODO Obrir pressupostos del client
        });

        controller.getPagina().getTaula().setOnFactures(client -> {
            // TODO Obrir factures del client
        });
    }

    private void filtrar() {
        boolean actius = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactius = controller.getPagina().getToolbar().getChkInactius().isSelected();
        if (!actius && !inactius) {
            controller.getPagina().getToolbar().getChkActius().setSelected(true);
            actius = true;
        }
        String text = controller.getPagina().getToolbar().getTxtBuscar().getText();
        controller.buscar(text == null ? "" : text.trim(), actius, inactius);
    }

    private void buscar(String text) {
        boolean actius = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactius = controller.getPagina().getToolbar().getChkInactius().isSelected();
        controller.buscar(text == null ? "" : text.trim(), actius, inactius);
    }
}