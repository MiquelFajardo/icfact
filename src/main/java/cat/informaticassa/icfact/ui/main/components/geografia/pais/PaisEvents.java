package cat.informaticassa.icfact.ui.main.components.geografia.pais;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.service.pais.CrearPaisService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.dialogs.PaisDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class PaisEvents {
    private final PaisDialog dialog;
    private final PaisPane formulari;
    private final PaisBinder binder;
    private final CrearPaisService crearPaisService = new CrearPaisService();

    public PaisEvents(PaisDialog dialog) {
        this.dialog = dialog;
        this.formulari = dialog.getFormulari();
        this.binder = new PaisBinder(formulari);
        dialog.getBotoGuardar().setOnAction(e -> guardar());
    }

    private void guardar() {
        try {
            Pais pais = new Pais();
            binder.actualitzar(pais);
            crearPaisService.executar(pais);
            dialog.setPaisCreat(pais);
            dialog.close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            Alerta.error("No s'ha pogut crear el país.");
        }
    }
}