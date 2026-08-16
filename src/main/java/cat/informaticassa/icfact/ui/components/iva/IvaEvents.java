package cat.informaticassa.icfact.ui.components.iva;

import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.service.ModificarIvaService;
import cat.informaticassa.icfact.iva.service.CrearIvaService;
import cat.informaticassa.icfact.ui.components.dialogs.IvaDialog;
import cat.informaticassa.icfact.ui.util.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IvaEvents {
    private final IvaDialog dialog;
    private final IvaBinder binder;
    private final CrearIvaService crearIvaService = new CrearIvaService();
    private final ModificarIvaService modificarIvaService = new ModificarIvaService();
    private static final Logger logger = LoggerFactory.getLogger(IvaEvents.class);

    public IvaEvents(IvaDialog dialog) {
        this.dialog = dialog;
        IvaPane formulari = dialog.getFormulari();
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
            logger.error("Error inesperat desant l'IVA.", e);
            Alerta.error("No s'ha pogut desar l'IVA.");
        }
    }
}