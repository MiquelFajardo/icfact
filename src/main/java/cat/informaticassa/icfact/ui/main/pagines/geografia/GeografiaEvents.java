package cat.informaticassa.icfact.ui.main.pagines.geografia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.ui.dialogs.PaisDialog;
import cat.informaticassa.icfact.ui.dialogs.PoblacioDialog;
import cat.informaticassa.icfact.ui.dialogs.ProvinciaDialog;
import javafx.stage.Stage;

public class GeografiaEvents {
    private final GeografiaController controller;

    public GeografiaEvents(GeografiaController controller) {
        this.controller = controller;
        inicialitzar();
    }

    private void inicialitzar() {
        controller.getPagina().getCmbPais().setOnAction(e -> {
            controller.seleccionarPais();
            controller.carregarProvincies();
            boolean seleccionat = controller.getPaisSeleccionat() != null;
            controller.getPagina().getBotoModificarPais().setDisable(!seleccionat);
            controller.getPagina().getCmbProvincia().setDisable(!seleccionat);
            controller.getPagina().getBotoNovaProvincia().setDisable(!seleccionat);
            controller.getPagina().getBotoModificarProvincia().setDisable(true);
            controller.getPagina().getCmbPoblacio().getItems().clear();
            controller.getPagina().getCmbPoblacio().setDisable(true);
            controller.getPagina().getBotoNovaPoblacio().setDisable(true);
            controller.getPagina().getBotoModificarPoblacio().setDisable(true);
        });

        controller.getPagina().getCmbProvincia().setOnAction(e -> {
            controller.seleccionarProvincia();
            controller.carregarPoblacions();
            boolean seleccionat = controller.getProvinciaSeleccionada() != null;
            controller.getPagina().getBotoModificarProvincia().setDisable(!seleccionat);
            controller.getPagina().getCmbPoblacio().setDisable(!seleccionat);
            controller.getPagina().getBotoNovaPoblacio().setDisable(!seleccionat);
            controller.getPagina().getBotoModificarPoblacio().setDisable(true);
        });

        controller.getPagina().getCmbPoblacio().setOnAction(e -> {
            controller.seleccionarPoblacio();
            controller.getPagina()
                    .getBotoModificarPoblacio()
                    .setDisable(controller.getPoblacioSeleccionada() == null);
        });

        controller.getPagina().getBotoNouPais().setOnAction(e -> {
            PaisDialog dialog = new PaisDialog();
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            controller.carregarPaisos();
            if (dialog.getPais() != null) {
                controller.getPagina()
                        .getCmbPais()
                        .getSelectionModel()
                        .select(dialog.getPais().getNom());
                controller.seleccionarPais();
                controller.carregarProvincies();
            }
        });

        controller.getPagina().getBotoModificarPais().setOnAction(e -> {
            Pais pais = controller.getPaisSeleccionat();
            if (pais == null) {
                return;
            }
            PaisDialog dialog = new PaisDialog(pais);
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            controller.carregarPaisos();
            controller.getPagina()
                    .getCmbPais()
                    .getSelectionModel()
                    .select(pais.getNom());
            controller.seleccionarPais();
            controller.carregarProvincies();
        });

        controller.getPagina().getBotoNovaProvincia().setOnAction(e -> {
            if (controller.getPaisSeleccionat() == null) {
                return;
            }
            ProvinciaDialog dialog = new ProvinciaDialog(controller.getPaisSeleccionat());
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            controller.carregarProvincies();
            if (dialog.getProvincia() != null) {
                controller.getPagina()
                        .getCmbProvincia()
                        .getSelectionModel()
                        .select(dialog.getProvincia().getNom());
                controller.seleccionarProvincia();
                controller.carregarPoblacions();
            }
        });

        controller.getPagina().getBotoModificarProvincia().setOnAction(e -> {
            if (controller.getProvinciaSeleccionada() == null) {
                return;
            }
            ProvinciaDialog dialog =
                    new ProvinciaDialog(
                            controller.getPaisSeleccionat(),
                            controller.getProvinciaSeleccionada()
                    );
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            controller.carregarProvincies();
            if (dialog.getProvincia() != null) {
                controller.getPagina()
                        .getCmbProvincia()
                        .getSelectionModel()
                        .select(dialog.getProvincia().getNom());
                controller.seleccionarProvincia();
                controller.carregarPoblacions();
            }
        });

        controller.getPagina().getBotoNovaPoblacio().setOnAction(e -> {
            if (controller.getProvinciaSeleccionada() == null) {
                return;
            }
            PoblacioDialog dialog = new PoblacioDialog(controller.getProvinciaSeleccionada());
            dialog.initOwner(
                    (Stage) controller.getPagina().getScene().getWindow()
            );
            dialog.showAndWait();
            controller.carregarPoblacions();
            if (dialog.getPoblacio() != null) {

                controller.getPagina()
                        .getCmbPoblacio()
                        .getSelectionModel()
                        .select(dialog.getPoblacio().getNom());
            }
        });

        controller.getPagina().getBotoModificarPoblacio().setOnAction(e -> {
            if (controller.getPoblacioSeleccionada() == null) {
                return;
            }
            PoblacioDialog dialog =
                    new PoblacioDialog(
                            controller.getProvinciaSeleccionada(),
                            controller.getPoblacioSeleccionada()
                    );
            dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            controller.carregarPoblacions();
            if (dialog.getPoblacio() != null) {
                controller.getPagina()
                        .getCmbPoblacio()
                        .getSelectionModel()
                        .select(dialog.getPoblacio().getNom());
            }
        });
    }
}