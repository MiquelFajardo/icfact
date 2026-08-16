package cat.informaticassa.icfact.ui.main.pagines.iva.controller;

import cat.informaticassa.icfact.ui.components.dialogs.IvaDialog;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class IvaEvents {
    private final IvaController controller;
    private final PauseTransition pausaBuscar = new PauseTransition(Duration.millis(300));

    public IvaEvents(IvaController controller) {
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
            IvaDialog dialog = new IvaDialog();
            dialog.initOwner(controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            controller.carregarActius();
        });

        controller.getPagina().getTaula().setOnModificar(iva -> {
            IvaDialog dialog = new IvaDialog(iva);
            dialog.initOwner(controller.getPagina().getScene().getWindow());
            dialog.showAndWait();

            controller.carregarActius();
        });
    }

    private void filtrar() {

        boolean actius = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactius = controller.getPagina().getToolbar().getChkInactius().isSelected();

        if (!actius && !inactius) {
            controller.getPagina().getToolbar().getChkActius().setSelected(true);
            actius = true;
        }
        if (actius && inactius) {
            controller.carregarTots();
        } else if (actius) {
            controller.carregarActius();
        } else {
            controller.carregarInactius();
        }
    }

    private void buscar(String text) {
        boolean actius = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactius = controller.getPagina().getToolbar().getChkInactius().isSelected();
        controller.buscar(text.trim(), actius, inactius);
    }
}