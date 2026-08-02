package cat.informaticassa.icfact.ui.main.pagines.client.controller;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.ui.main.pagines.client.altaClient.ClientPage;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ClientEvents {

    private final ClientController controller;

    public ClientEvents(ClientController controller) {

        this.controller = controller;

        inicialitzar();
    }

    private void inicialitzar() {
        controller.getPagina().getToolbar().getChkActius().setOnAction(e -> filtrar());
        controller.getPagina().getToolbar().getChkInactius().setOnAction(e -> filtrar());
        controller.getPagina().getToolbar().getTxtBuscar().textProperty().addListener(
                (obs, oldValue, newValue) -> buscar(newValue)
        );

        controller.getPagina().getToolbar().getBotoNou().setOnAction(e -> {
            Stage stage = (Stage) controller.getPagina().getScene().getWindow();
            stage.setScene(
                    new Scene(
                            new ClientPage(),
                            stage.getScene().getWidth(),
                            stage.getScene().getHeight()
                    )
            );

        });

        controller.getPagina().getTaula().setOnMouseClicked(e -> {
            if (e.getClickCount() != 2) {
                return;
            }
            Client client = controller.getPagina().getTaula()
                    .getSelectionModel()
                    .getSelectedItem();

            if (client == null) {
                return;
            }

            // TODO Obrir fitxa del client
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

        // Ho implementarem quan fem el servei de cerca.
    }
}