package cat.informaticassa.icfact.ui.main.pagines.formaPagament.controller;

import cat.informaticassa.icfact.ui.components.dialogs.FormaPagamentDialog;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FormaPagamentEvents {
    private final FormaPagamentController controller;
    private final PauseTransition pausaBuscar = new PauseTransition(Duration.millis(300));

    public FormaPagamentEvents(FormaPagamentController controller) {
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
            FormaPagamentDialog dialog = new FormaPagamentDialog();
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            controller.carregarActius();
        });

        controller.getPagina().getTaula().setOnModificar(fp -> {
            FormaPagamentDialog dialog = new FormaPagamentDialog(fp);
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
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
        String text = controller.getPagina().getToolbar().getTxtBuscar().getText();
        controller.buscar(text == null ? "" : text.trim(), actius, inactius);
    }

    private void buscar(String text) {
        boolean actius = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactius = controller.getPagina().getToolbar().getChkInactius().isSelected();
        controller.buscar(text == null ? "" : text.trim(), actius, inactius);
    }
}