package cat.informaticassa.icfact.ui.main.components.geografia.pais;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.service.pais.CrearPaisService;
import cat.informaticassa.icfact.geografia.service.pais.ModificarPaisService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.dialogs.PaisDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class PaisEvents {

    private final PaisDialog dialog;
    private final PaisPane formulari;
    private final PaisBinder binder;

    private final CrearPaisService crearPaisService = new CrearPaisService();
    private final ModificarPaisService modificarPaisService = new ModificarPaisService();

    public PaisEvents(PaisDialog dialog) {
        this.dialog = dialog;
        this.formulari = dialog.getFormulari();
        this.binder = new PaisBinder(formulari);
        dialog.getBotoGuardar().setOnAction(e -> guardar());
    }

    private void guardar() {
        try {
            Pais pais;
            if (dialog.esEdicio()) {
                pais = dialog.getPais();
            } else {
                pais = new Pais();
            }
            binder.actualitzar(pais);
            if (dialog.esEdicio()) {
                modificarPaisService.executar(pais);
            } else {
                crearPaisService.executar(pais);
            }
            dialog.setPais(pais);
            dialog.close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            Alerta.error("No s'ha pogut guardar el país.");
        }
    }
}