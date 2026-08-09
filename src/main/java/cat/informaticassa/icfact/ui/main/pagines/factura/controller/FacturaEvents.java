package cat.informaticassa.icfact.ui.main.pagines.factura.controller;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.service.*;
import cat.informaticassa.icfact.ui.components.dialogs.FacturaDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PagamentDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PagamentsDialog;
import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FacturaEvents {
    private final FacturaController controller;
    private final PauseTransition pausaBuscar = new PauseTransition(Duration.millis(300));
    private final ObtenirFacturaService obtenirFacturaService = new ObtenirFacturaService();
    private final DuplicarFacturaService duplicarFacturaService = new DuplicarFacturaService();
    private final CanviarEstatFacturaService canviarEstatFacturaService = new CanviarEstatFacturaService();
    private final ObrirPdfFacturaService obrirPdfFacturaService = new ObrirPdfFacturaService();
    private final GenerarPdfFacturaService generarPdfFacturaService = new GenerarPdfFacturaService();

    public FacturaEvents(FacturaController controller) {
        this.controller = controller;
        inicialitzar();
    }

    private void inicialitzar() {
        controller.getPagina().getToolbar().getChkActius().setOnAction(e -> filtrar());
        controller.getPagina().getToolbar().getChkInactius() .setOnAction(e -> filtrar());

        controller.getPagina().getToolbar().getTxtBuscar().textProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (controller.getClientFiltrat() != null) {
                        return;
                    }
                    pausaBuscar.stop();
                    pausaBuscar.setOnFinished(e -> buscar(newValue));
                    pausaBuscar.playFromStart();
                });

        controller.getPagina().getToolbar().getBotoNou().setOnAction(e -> {
                    FacturaDialog dialog =  new FacturaDialog();
                    dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
                    dialog.showAndWait();
                    buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
                });

        controller.getPagina().getTaula().setOnModificar(factura -> {
            try {
                Factura facturaCompleta = obtenirFacturaService.executar(factura.getId());
                FacturaDialog dialog =new FacturaDialog(facturaCompleta);
                dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
                dialog.showAndWait();
                buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
            } catch (Exception ex) {
                Alerta.error((Stage) controller.getPagina().getScene().getWindow(), ex.getMessage());
            }
        });

        controller.getPagina().getTaula().setOnObrirPdf(factura -> {
            try {
                obrirPdfFacturaService.executar(factura);
            } catch (Exception ex) {
                Alerta.error((Stage) controller.getPagina().getScene().getWindow(), ex.getMessage());
            }
        });

        controller.getPagina().getTaula().setOnDuplicar(factura -> {
            try {
                Factura nova = duplicarFacturaService.executar(factura.getId());
                FacturaDialog dialog = new FacturaDialog(nova);
                dialog.initOwner((Stage) controller.getPagina().getScene().getWindow());
                dialog.showAndWait();
                buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
            } catch (Exception ex) {
                Alerta.error((Stage) controller.getPagina().getScene().getWindow(), ex.getMessage());
            }
        });

        controller.getPagina().getTaula().setOnAnullar(factura -> {
            Stage stage =(Stage) controller.getPagina().getScene().getWindow();
            if (factura.getPagaments() != null && !factura.getPagaments().isEmpty()) {
                Alerta.error(stage,"No es pot anul·lar una factura que té pagaments.");
                return;
            }
            boolean resposta = Alerta.confirmar(stage,"Anul·lar factura","Vols anul·lar aquesta factura?");
            if (!resposta) {
                return;
            }
            try {
                canviarEstatFacturaService.executar(factura.getId(), EstatFactura.ANULADA);
                regenerarPdf(factura.getId());
                buscar(controller.getPagina().getToolbar().getTxtBuscar().getText()
                );
            } catch (Exception ex) {
                Alerta.error(stage, ex.getMessage());
            }
        });

        controller.getPagina().getTaula().setOnAfegirPagament(factura -> {
            Stage stage = (Stage) controller.getPagina().getScene().getWindow();
            try {
                PagamentDialog dialog = new PagamentDialog(factura);
                dialog.initOwner(stage);
                dialog.showAndWait();
                buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
            } catch (Exception ex) {
                Alerta.error(stage, ex.getMessage());
            }
        });

        controller.getPagina().getTaula().setOnVeurePagaments(factura -> {
            Stage stage = (Stage) controller.getPagina().getScene().getWindow();
            try {
                PagamentsDialog dialog = new PagamentsDialog(factura);
                dialog.initOwner(stage);
                dialog.showAndWait();
            } catch (Exception ex) {
                Alerta.error(stage, ex.getMessage());
            }
        });
    }

    private void filtrar() {
        boolean actives = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactives = controller.getPagina().getToolbar().getChkInactius().isSelected();
        if (!actives && !inactives) {
            controller.getPagina().getToolbar().getChkActius().setSelected(true);
            actives = true;
        }
        buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
    }

    private void buscar(String text) {
        boolean actives = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactives = controller.getPagina().getToolbar().getChkInactius().isSelected();
        controller.buscar( text == null ? "" : text.trim(), actives, inactives, controller.getClientFiltrat());
    }

    private void regenerarPdf(Long facturaId) {
        try {
            generarPdfFacturaService.executar(facturaId);
        } catch (Exception ex) {
            Alerta.error((Stage) controller.getPagina().getScene().getWindow(),
                    "No s'ha pogut actualitzar el PDF de la factura:\n" + ex.getMessage());
        }
    }
}