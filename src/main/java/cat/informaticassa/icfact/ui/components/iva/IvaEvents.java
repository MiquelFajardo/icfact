package cat.informaticassa.icfact.ui.components.iva;

import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.service.ModificarIvaService;
import cat.informaticassa.icfact.iva.service.CrearIvaService;
import cat.informaticassa.icfact.ui.components.dialogs.IvaDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class IvaEvents {
    private final IvaDialog dialog;
    private final IvaPane formulari;
    private final IvaBinder binder;
    private final CrearIvaService crearIvaService = new CrearIvaService();
    private final ModificarIvaService modificarIvaService = new ModificarIvaService();

    public IvaEvents(IvaDialog dialog) {
        this.dialog = dialog;
        this.formulari = dialog.getFormulari();
        this.binder = new IvaBinder(formulari);
        dialog.getBotoGuardar().setOnAction(e -> guardar());
    }

    private void guardar() {
        try {
            Iva iva;
            if (dialog.esEdicio()) {
                iva = dialog.getIva();
                binder.actualitzar(iva);
                modificarIvaService.executar(iva);
            } else {
                iva = new Iva();
                binder.actualitzar(iva);
                crearIvaService.executar(iva);
            }
            dialog.setIva(iva);
            dialog.close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerta.error("No s'ha pogut desar l'IVA.");
        }
    }
}