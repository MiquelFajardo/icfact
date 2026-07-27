package cat.informaticassa.icfact.ui.main.components.geografia.provincia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.provincia.CrearProvinciaService;
import cat.informaticassa.icfact.ui.dialogs.ProvinciaDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class ProvinciaEvents {

    private final ProvinciaDialog dialog;
    private final ProvinciaPane vista;
    private final ProvinciaBinder binder;

    private final CrearProvinciaService crearProvinciaService = new CrearProvinciaService();

    public ProvinciaEvents(ProvinciaDialog dialog) {
        this.dialog = dialog;
        this.vista = dialog.getFormulari();
        this.binder = new ProvinciaBinder(vista);
        dialog.getBotoGuardar().setOnAction(e -> guardar());
        dialog.getBotoCancelar().setOnAction(e -> dialog.close());
    }

    public void mostrar(Pais pais) {
        binder.mostrar(pais);
    }

    private void guardar() {
        try {
            Provincia provincia = new Provincia();
            binder.actualitzar(provincia);
            crearProvinciaService.executar(provincia);
            dialog.setProvinciaCreada(provincia);
            dialog.close();
        } catch (Exception e) {
            Alerta.error(e.getMessage());
        }
    }
}