package cat.informaticassa.icfact.ui.main.components.geografia.provincia;

import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.provincia.CrearProvinciaService;
import cat.informaticassa.icfact.geografia.service.provincia.ModificarProvinciaService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.dialogs.ProvinciaDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class ProvinciaEvents {
    private final ProvinciaDialog dialog;
    private final ProvinciaPane formulari;
    private final ProvinciaBinder binder;
    private final CrearProvinciaService crearProvinciaService = new CrearProvinciaService();
    private final ModificarProvinciaService modificarProvinciaService = new ModificarProvinciaService();

    public ProvinciaEvents(ProvinciaDialog dialog) {
        this.dialog = dialog;
        this.formulari = dialog.getFormulari();
        this.binder = new ProvinciaBinder(formulari);
        if (dialog.getPais() != null) {
            formulari.setPais(dialog.getPais());
        }
        dialog.getBotoGuardar().setOnAction(e -> guardar());
        dialog.getBotoCancelar().setOnAction(e -> dialog.close());
    }

    private void guardar() {
        try {
            Provincia provincia;
            if (dialog.esEdicio()) {
                provincia = dialog.getProvincia();
            } else {
                provincia = new Provincia();
                provincia.setPais(dialog.getPais());
            }
            binder.actualitzar(provincia);
            if (dialog.esEdicio()) {
                modificarProvinciaService.executar(provincia);
            } else {
                crearProvinciaService.executar(provincia);
            }
            dialog.setProvincia(provincia);
            dialog.close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            Alerta.error("No s'ha pogut guardar la província.");
        }
    }
}