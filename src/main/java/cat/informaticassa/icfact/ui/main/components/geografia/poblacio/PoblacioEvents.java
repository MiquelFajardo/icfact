package cat.informaticassa.icfact.ui.main.components.geografia.poblacio;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.service.poblacio.CrearPoblacioService;
import cat.informaticassa.icfact.geografia.service.poblacio.ModificarPoblacioService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.dialogs.PoblacioDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class PoblacioEvents {
    private final PoblacioDialog dialog;
    private final PoblacioPane formulari;
    private final PoblacioBinder binder;
    private final CrearPoblacioService crearPoblacioService = new CrearPoblacioService();
    private final ModificarPoblacioService modificarPoblacioService = new ModificarPoblacioService();

    public PoblacioEvents(PoblacioDialog dialog) {
        this.dialog = dialog;
        this.formulari = dialog.getFormulari();
        this.binder = new PoblacioBinder(formulari);
        if (dialog.getProvincia() != null) {
            formulari.setProvincia(dialog.getProvincia());
        }
        dialog.getBotoGuardar().setOnAction(e -> guardar());
        dialog.getBotoCancelar().setOnAction(e -> dialog.close());
    }

    private void guardar() {
        try {
            Poblacio poblacio;
            if (dialog.esEdicio()) {
                poblacio = dialog.getPoblacio();
            } else {
                poblacio = new Poblacio();
                poblacio.setProvincia(dialog.getProvincia());
            }
            binder.actualitzar(poblacio);
            if (dialog.esEdicio()) {
                modificarPoblacioService.executar(poblacio);
            } else {
                crearPoblacioService.executar(poblacio);
            }
            dialog.setPoblacio(poblacio);
            dialog.close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            Alerta.error("No s'ha pogut guardar la població.");
        }
    }
}