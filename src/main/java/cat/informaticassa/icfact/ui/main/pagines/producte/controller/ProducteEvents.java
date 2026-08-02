package cat.informaticassa.icfact.ui.main.pagines.producte.controller;

import cat.informaticassa.icfact.ui.components.dialogs.ProducteDialog;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProducteEvents {
    private final ProducteController controller;
    private final PauseTransition pausaBuscar = new PauseTransition(Duration.millis(300));

    public ProducteEvents(ProducteController controller) {
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
            ProducteDialog dialog = new ProducteDialog();
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
        });

        controller.getPagina().getTaula().setOnModificar(producte -> {
            ProducteDialog dialog = new ProducteDialog(producte);
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
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