package cat.informaticassa.icfact.ui.main.pagines.pressupost.controller;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.service.*;
import cat.informaticassa.icfact.ui.components.dialogs.FacturaDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PagamentDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PagamentsDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PressupostDialog;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PressupostEvents {
    private final PressupostController controller;
    private final PauseTransition pausaBuscar = new PauseTransition(Duration.millis(300));
    private final ObrirPdfPressupostService obrirPdfService = new ObrirPdfPressupostService();
    private final ObtenirPressupostService obtenirPressupostService = new ObtenirPressupostService();
    private final DuplicarPressupostService duplicarPressupostService = new DuplicarPressupostService();
    private final CanviarEstatPressupostService canviarEstatPressupostService = new CanviarEstatPressupostService();
    private final ConvertirPressupostAFacturaService convertirPressupostAFacturaService = new ConvertirPressupostAFacturaService();
    private final PagamentRepository pagamentRepository = new PagamentRepository();

    public PressupostEvents(PressupostController controller) {
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
            PressupostDialog dialog = new PressupostDialog();
            dialog.initOwner(controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
        });

        controller.getPagina().getTaula().setOnModificar(pressupost -> {
            Pressupost pressupostComplet = obtenirPressupostService.executar(pressupost.getId());
            PressupostDialog dialog = new PressupostDialog(pressupostComplet);
            dialog.initOwner(controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
        });

        controller.getPagina().getTaula().setOnObrirPdf(pressupost -> {
            try {
                obrirPdfService.executar(pressupost);
            } catch (Exception ex) {
                Alerta.error(controller.getPagina().getScene().getWindow(), ex.getMessage());
            }
        });

        controller.getPagina().getTaula().setOnDuplicar(pressupost -> {
            Pressupost nou = duplicarPressupostService.executar(pressupost.getId());
            PressupostDialog dialog = new PressupostDialog(nou);
            dialog.initOwner(controller.getPagina().getScene().getWindow());
            dialog.showAndWait();
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
        });

        controller.getPagina().getTaula().setOnAcceptar(pressupost -> {
            boolean resposta = Alerta.confirmar(controller.getPagina().getScene().getWindow(),
                    "Acceptar pressupost",
                    "Vols marcar aquest pressupost com a ACCEPTAT?");
            if (!resposta) {
                return;
            }
            canviarEstatPressupostService.executar(pressupost.getId(), EstatPressupost.ACCEPTAT);
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
        });

        controller.getPagina().getTaula().setOnRebutjar(pressupost -> {
            boolean resposta = Alerta.confirmar(controller.getPagina().getScene().getWindow(),
                    "Rebutjar pressupost",
                    "Vols marcar aquest pressupost com a REBUTJAT?");
            if (!resposta) {
                return;
            }
            canviarEstatPressupostService.executar(pressupost.getId(), EstatPressupost.REBUTJAT);
            buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
        });

        controller.getPagina().getTaula().setOnCrearFactura(pressupost -> {
            Stage stage = (Stage) controller.getPagina().getScene().getWindow();
            boolean confirmar = Alerta.confirmar(stage,"Convertir pressupost","Vols convertir el pressupost "
                    + pressupost.getNumero() + " en una factura?");
            if (!confirmar) {
                return;
            }
            try {
                Factura factura = convertirPressupostAFacturaService.preparar(pressupost.getId());
                FacturaDialog dialog = new FacturaDialog(factura,true);
                dialog.initOwner(stage);
                dialog.showAndWait();
                if (!dialog.isDesadaCorrectament()) {
                    return;
                }
                Factura facturaGuardada = dialog.getFactura();
                pagamentRepository.associarPagamentsAFactura(pressupost, facturaGuardada);
                convertirPressupostAFacturaService.marcarPressupostFacturat(pressupost.getId());
                controller.buscar(
                        controller.getPagina()
                                .getToolbar()
                                .getTxtBuscar()
                                .getText(),
                        controller.getPagina()
                                .getToolbar()
                                .getChkActius()
                                .isSelected(),
                        controller.getPagina()
                                .getToolbar()
                                .getChkInactius()
                                .isSelected()
                );
            } catch (Exception ex) {

                Alerta.error(
                        stage,
                        ex.getMessage()
                );
            }
        });


        controller.getPagina().getTaula().setOnAfegirPagament(pressupost -> {
            Stage stage =(Stage) controller.getPagina().getScene().getWindow();
            try {
                PagamentDialog dialog = new PagamentDialog(pressupost);
                dialog.initOwner(stage);
                dialog.showAndWait();
                buscar( controller.getPagina().getToolbar() .getTxtBuscar().getText());
            } catch (Exception ex) {
                Alerta.error(stage, ex.getMessage());
            }
        });

        controller.getPagina().getTaula().setOnVeurePagaments(pressupost -> {
            Stage stage = (Stage) controller.getPagina().getScene().getWindow();
            PagamentsDialog dialog = new PagamentsDialog(pressupost);
            dialog.initOwner(stage);
            dialog.showAndWait();
        });

    }

    private void filtrar() {
        boolean actius = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactius = controller.getPagina().getToolbar().getChkInactius().isSelected();
        if (!actius && !inactius) {
            controller.getPagina().getToolbar().getChkActius().setSelected(true);
        }
        buscar(controller.getPagina().getToolbar().getTxtBuscar().getText());
    }

    private void buscar(String text) {
        boolean actius = controller.getPagina().getToolbar().getChkActius().isSelected();
        boolean inactius = controller.getPagina().getToolbar().getChkInactius().isSelected();
        controller.buscar(text == null ? "" : text.trim(), actius, inactius);
    }
}