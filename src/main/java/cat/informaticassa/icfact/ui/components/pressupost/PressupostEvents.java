package cat.informaticassa.icfact.ui.components.pressupost;

import cat.informaticassa.icfact.pdf.service.ObrirPdfService;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.service.GenerarPdfPressupostService;
import cat.informaticassa.icfact.pressupost.service.GuardarPressupostService;
import cat.informaticassa.icfact.ui.components.dialogs.PressupostDialog;
import cat.informaticassa.icfact.pressupost.service.ModificarPressupostService;
import cat.informaticassa.icfact.ui.util.Alerta;

import java.nio.file.Path;

public class PressupostEvents {
    private final PressupostDialog dialog;
    private final GuardarPressupostService guardarService = new GuardarPressupostService();
    private final GenerarPdfPressupostService generarPdfPressupostService = new GenerarPdfPressupostService();
    private final ModificarPressupostService modificarService = new ModificarPressupostService();

    public PressupostEvents(PressupostDialog dialog) {
        this.dialog = dialog;
        inicialitzar();
    }

    private void inicialitzar() {
        dialog.getBotoGuardar().setDisable(!dialog.getDirtyTracker().estaModificat());
        dialog.getDirtyTracker()
                .modificatProperty()
                .addListener((obs, anterior, modificat) -> {
                    dialog.getBotoGuardar().setDisable(!modificat);
                    dialog.getBotoGenerarPdf().setDisable(!modificat);
                });
        dialog.getBotoGuardar().setOnAction(e -> guardar(false));
        dialog.getBotoGenerarPdf().setOnAction(e -> guardar(true));
    }

    private void guardar(boolean generarPdf) {
        try {
            Pressupost pressupost = dialog.getPressupost();
            new PressupostBinder(dialog.getFormulari()).guardar(pressupost);
            if (generarPdf) {
                pressupost.setEstat(EstatPressupost.ENVIAT);
            } else {
                pressupost.setEstat(EstatPressupost.ESBORRANY);
            }
            if (dialog.esEdicio()) {
                modificarService.executar(pressupost);
            } else {
                guardarService.executar(pressupost);
            }
            if (generarPdf) {
                Path pdf = generarPdfPressupostService.executar(pressupost);
                new ObrirPdfService().executar(pdf);
            }
            dialog.getDirtyTracker().marcarDesat();
            dialog.close();
        } catch (Exception ex) {
            Alerta.error(dialog, ex.getMessage());
        }
    }
}