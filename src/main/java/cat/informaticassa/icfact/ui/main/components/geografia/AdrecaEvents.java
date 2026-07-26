package cat.informaticassa.icfact.ui.main.components.geografia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.BuscarPoblacionsPerProvinciaService;
import cat.informaticassa.icfact.geografia.service.BuscarProvinciesPerPaisService;

public class AdrecaEvents {
    private final AdrecaPane vista;
    private final BuscarProvinciesPerPaisService buscarProvinciesPerPaisService = new BuscarProvinciesPerPaisService();
    private final BuscarPoblacionsPerProvinciaService buscarPoblacionsPerProvinciaService = new BuscarPoblacionsPerProvinciaService();

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
        Pais pais = vista.getCmbPais().getValue();
        vista.getCmbProvincia().getItems().clear();
        vista.getCmbPoblacio().getItems().clear();

        if (pais == null) {
            return;
        }

        vista.getCmbProvincia().getItems().setAll(buscarProvinciesPerPaisService.executar(pais));
    }

    private void canviProvincia() {
        Provincia provincia = vista.getCmbProvincia().getValue();
        vista.getCmbPoblacio().getItems().clear();

        if (provincia == null) {
            return;
        }

        vista.getCmbPoblacio().getItems().setAll(buscarPoblacionsPerProvinciaService.executar(provincia));
    }

    private void nouPais() {
        // TODO Obrir formulari Nou País
    }

    private void novaProvincia() {
        // TODO Obrir formulari Nova Província
    }

    private void novaPoblacio() {
        // TODO Obrir formulari Nova Població
    }
}