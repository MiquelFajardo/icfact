package cat.informaticassa.icfact.ui.main.components.geografia.poblacio;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.poblacio.CrearPoblacioService;
import cat.informaticassa.icfact.ui.dialogs.PoblacioDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class PoblacioEvents {

    private final PoblacioDialog dialog;
    private final PoblacioPane vista;
    private final PoblacioBinder binder;

    private final CrearPoblacioService crearPoblacioService = new CrearPoblacioService();

    public PoblacioEvents(PoblacioDialog dialog) {
        this.dialog = dialog;
        this.vista = dialog.getFormulari();
        this.binder = new PoblacioBinder(vista);
        dialog.getBotoGuardar().setOnAction(e -> guardar());
        dialog.getBotoCancelar().setOnAction(e -> dialog.close());
    }

    public void mostrar(Provincia provincia) {
        binder.mostrar(provincia);
    }

    private void guardar() {
        try {
            Poblacio poblacio = new Poblacio();
            binder.actualitzar(poblacio);
            crearPoblacioService.executar(poblacio);
            dialog.setPoblacioCreada(poblacio);
            dialog.close();
        } catch (Exception e) {
            Alerta.error(e.getMessage());
        }
    }
}