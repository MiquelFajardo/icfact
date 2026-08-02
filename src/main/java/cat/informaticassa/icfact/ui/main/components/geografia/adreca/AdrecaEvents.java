package cat.informaticassa.icfact.ui.main.components.geografia.adreca;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.pais.BuscarPaisosService;
import cat.informaticassa.icfact.geografia.service.poblacio.BuscarPoblacionsPerProvinciaService;
import cat.informaticassa.icfact.geografia.service.provincia.BuscarProvinciesPerPaisService;
import cat.informaticassa.icfact.ui.dialogs.PaisDialog;
import cat.informaticassa.icfact.ui.dialogs.PoblacioDialog;
import cat.informaticassa.icfact.ui.dialogs.ProvinciaDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class AdrecaEvents {

    private final AdrecaPane vista;

    private final BuscarPaisosService buscarPaisosService = new BuscarPaisosService();
    private final BuscarProvinciesPerPaisService buscarProvinciesPerPaisService = new BuscarProvinciesPerPaisService();
    private final BuscarPoblacionsPerProvinciaService buscarPoblacionsPerProvinciaService = new BuscarPoblacionsPerProvinciaService();
    private boolean carregant = false;

    public AdrecaEvents(AdrecaPane vista) {
        this.vista = vista;
        inicialitzarEvents();
    }

    private void inicialitzarEvents() {
        vista.getCmbPais().setOnAction(e -> canviPais());
        vista.getCmbProvincia().setOnAction(e -> canviProvincia());
        vista.getBotoNouPais().setOnAction(e -> nouPais());
        vista.getBotoNovaProvincia().setOnAction(e -> novaProvincia());
        vista.getBotoNovaPoblacio().setOnAction(e -> novaPoblacio());
    }

    private void canviPais() {
        if (carregant) {
            return;
        }
        Pais pais = vista.getCmbPais().getValue();
        vista.getCmbProvincia().getItems().clear();
        vista.getCmbProvincia().setValue(null);
        vista.getCmbPoblacio().getItems().clear();
        vista.getCmbPoblacio().setValue(null);
        if (pais == null) {
            return;
        }
        vista.getCmbProvincia().getItems().setAll(
                buscarProvinciesPerPaisService.executar(pais)
        );
    }

    private void canviProvincia() {
        if (carregant) {
            return;
        }
        Provincia provincia = vista.getCmbProvincia().getValue();
        vista.getCmbPoblacio().getItems().clear();
        vista.getCmbPoblacio().setValue(null);
        if (provincia == null) {
            return;
        }
        vista.getCmbPoblacio().getItems().setAll(
                buscarPoblacionsPerProvinciaService.executar(provincia)
        );
    }

    private void nouPais() {
        PaisDialog dialog = new PaisDialog();
        dialog.showAndWait();
        Pais pais = dialog.getPais();
        if (pais == null) {
            return;
        }
        vista.getCmbPais().getItems().setAll(
                buscarPaisosService.executar()
        );
        vista.getCmbPais().setValue(pais);
    }

    private void novaProvincia() {
        Pais pais = vista.getCmbPais().getValue();
        if (pais == null) {
            Alerta.error("Primer has de seleccionar un país.");
            return;
        }
        ProvinciaDialog dialog = new ProvinciaDialog(pais);
        dialog.showAndWait();
        Provincia provincia = dialog.getProvincia();
        if (provincia == null) {
            return;
        }
        vista.getCmbProvincia().getItems().setAll(
                buscarProvinciesPerPaisService.executar(pais)
        );
        vista.getCmbProvincia().setValue(provincia);
    }

    private void novaPoblacio() {
        Provincia provincia = vista.getCmbProvincia().getValue();
        if (provincia == null) {
            Alerta.error("Primer has de seleccionar una província.");
            return;
        }
        PoblacioDialog dialog = new PoblacioDialog(provincia);
        dialog.showAndWait();
        Poblacio poblacio = dialog.getPoblacio();
        if (poblacio == null) {
            return;
        }
        vista.getCmbPoblacio().getItems().setAll(
                buscarPoblacionsPerProvinciaService.executar(provincia)
        );
        vista.getCmbPoblacio().setValue(poblacio);
    }
}