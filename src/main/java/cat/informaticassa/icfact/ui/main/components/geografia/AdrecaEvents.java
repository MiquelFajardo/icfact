package cat.informaticassa.icfact.ui.main.components.geografia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.pais.BuscarPaisosService;
import cat.informaticassa.icfact.geografia.service.poblacio.BuscarPoblacionsPerProvinciaService;
import cat.informaticassa.icfact.geografia.service.provincia.BuscarProvinciesPerPaisService;
import cat.informaticassa.icfact.ui.dialogs.PaisDialog;

public class AdrecaEvents {
    private final AdrecaPane vista;
    private final BuscarProvinciesPerPaisService buscarProvinciesPerPaisService = new BuscarProvinciesPerPaisService();
    private final BuscarPoblacionsPerProvinciaService buscarPoblacionsPerProvinciaService = new BuscarPoblacionsPerProvinciaService();
    private final BuscarPaisosService buscarPaisosService = new BuscarPaisosService();
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
        vista.getCmbPoblacio().getItems().clear();
        if (pais == null) {
            return;
        }
        vista.getCmbProvincia().getItems().setAll(buscarProvinciesPerPaisService.executar(pais));
    }

    private void canviProvincia() {
        if (carregant) {
            return;
        }
        Provincia provincia = vista.getCmbProvincia().getValue();
        vista.getCmbPoblacio().getItems().clear();
        if (provincia == null) {
            return;
        }
        vista.getCmbPoblacio().getItems().setAll(buscarPoblacionsPerProvinciaService.executar(provincia));
    }

    private void nouPais() {
        PaisDialog dialog = new PaisDialog();
        dialog.showAndWait();
        Pais pais = dialog.getPaisCreat();
        if (pais == null) {
            return;
        }
        vista.getCmbPais().getItems().setAll(buscarPaisosService.executar());
        vista.getCmbPais().setValue(pais);
    }

    private void novaProvincia() {
        // TOT
    }

    private void novaPoblacio() {
        // TOT
    }
}