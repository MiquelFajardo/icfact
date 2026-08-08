package cat.informaticassa.icfact.ui.components.factura;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.service.CrearFacturaService;
import cat.informaticassa.icfact.factura.service.GenerarPdfFacturaService;
import cat.informaticassa.icfact.factura.service.ModificarFacturaService;
import cat.informaticassa.icfact.pdf.service.ObrirPdfService;
import cat.informaticassa.icfact.ui.components.dialogs.FacturaDialog;
import cat.informaticassa.icfact.ui.util.Alerta;
import java.nio.file.Path;

public class FacturaEvents {
    private final FacturaDialog dialog;
    private final CrearFacturaService crearService =new CrearFacturaService();
    private final ModificarFacturaService modificarService = new ModificarFacturaService();
    private final GenerarPdfFacturaService generarPdfService = new GenerarPdfFacturaService();
    private final ObrirPdfService obrirPdfService =  new ObrirPdfService();

    public FacturaEvents(FacturaDialog dialog) {
        this.dialog = dialog;
        inicialitzar();
    }

    private void inicialitzar() {
        dialog.getBotoGuardar().setDisable(!dialog.getDirtyTracker().estaModificat());
        dialog.getBotoGenerarPdf().setDisable(!dialog.getDirtyTracker().estaModificat());
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
            Factura factura = dialog.getFactura();
            new FacturaBinder(dialog.getFormulari()).guardar(factura);
            if (generarPdf) {
                factura.setEstat(EstatFactura.EMESA);
            } else {
                factura.setEstat(EstatFactura.ESBORRANY);
            }
            if (dialog.esEdicio()) {
                modificarService.executar(factura);
            } else {
                crearService.executar(factura);
            }
            if (generarPdf) {
                Path pdf = generarPdfService.executar(factura.getId());
                obrirPdfService.executar(pdf);
            }
            dialog.getDirtyTracker().marcarDesat();
            dialog.setDesadaCorrectament(true);
            dialog.close();
        } catch (Exception ex) {
            Alerta.error(dialog, ex.getMessage());
        }
    }
}