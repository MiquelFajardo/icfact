package cat.informaticassa.icfact.ui.components.geografia.adreca;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.pais.BuscarPaisosService;
import cat.informaticassa.icfact.geografia.service.poblacio.BuscarPoblacionsPerProvinciaService;
import cat.informaticassa.icfact.geografia.service.provincia.BuscarProvinciesPerPaisService;
import cat.informaticassa.icfact.ui.components.dialogs.PaisDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PoblacioDialog;
import cat.informaticassa.icfact.ui.components.dialogs.ProvinciaDialog;
import javafx.stage.Stage;

public class AdrecaController {
    private final AdrecaPane pane;
    private final BuscarPaisosService buscarPaisosService = new BuscarPaisosService();
    private final BuscarProvinciesPerPaisService buscarProvinciesService = new BuscarProvinciesPerPaisService();
    private final BuscarPoblacionsPerProvinciaService buscarPoblacionsService = new BuscarPoblacionsPerProvinciaService();

    public AdrecaController(AdrecaPane pane) {
        this.pane = pane;
        inicialitzar();
    }

    private void inicialitzar() {
        carregarPaisos();
        pane.getCmbProvincia().setDisable(true);
        pane.getCmbPoblacio().setDisable(true);
        pane.getCmbPais().setOnAction(e -> canviPais());
        pane.getCmbProvincia().setOnAction(e -> canviProvincia());
        pane.getBotoNouPais().setOnAction(e -> nouPais());
        pane.getBotoNovaProvincia().setOnAction(e -> novaProvincia());
        pane.getBotoNovaPoblacio().setOnAction(e -> novaPoblacio());
    }

    private void carregarPaisos() {
        pane.getCmbPais().getItems().setAll(buscarPaisosService.executar());
    }

    public void canviPais() {
        Pais pais = pane.getCmbPais().getValue();
        pane.getCmbProvincia().getItems().clear();
        pane.getCmbProvincia().setValue(null);
        pane.getCmbPoblacio().getItems().clear();
        pane.getCmbPoblacio().setValue(null);
        pane.getCmbProvincia().setDisable(pais == null);
        pane.getCmbPoblacio().setDisable(true);
        if (pais == null) {
            return;
        }
        pane.getCmbProvincia().getItems().setAll(
                buscarProvinciesService.executar(pais)
        );
    }

    public void canviProvincia() {
        Provincia provincia = pane.getCmbProvincia().getValue();
        pane.getCmbPoblacio().getItems().clear();
        pane.getCmbPoblacio().setValue(null);
        pane.getCmbPoblacio().setDisable(provincia == null);
        if (provincia == null) {
            return;
        }
        pane.getCmbPoblacio().getItems().setAll(buscarPoblacionsService.executar(provincia));
    }

    private void nouPais() {
        PaisDialog dialog = new PaisDialog();
        dialog.initOwner((Stage) pane.getScene().getWindow());
        dialog.showAndWait();
        carregarPaisos();
        if (dialog.getPais() != null) {
            pane.getCmbPais().setValue(dialog.getPais());
            canviPais();
        }
    }

    private void novaProvincia() {
        Pais pais = pane.getCmbPais().getValue();
        if (pais == null) {
            return;
        }
        ProvinciaDialog dialog = new ProvinciaDialog(pais);
        dialog.initOwner((Stage) pane.getScene().getWindow());
        dialog.showAndWait();
        canviPais();
        if (dialog.getProvincia() != null) {
            pane.getCmbProvincia().setValue(dialog.getProvincia());
            canviProvincia();
        }
    }

    private void novaPoblacio() {
        Provincia provincia = pane.getCmbProvincia().getValue();
        if (provincia == null) {
            return;
        }
        PoblacioDialog dialog = new PoblacioDialog(provincia);
        dialog.initOwner((Stage) pane.getScene().getWindow());
        dialog.showAndWait();
        canviProvincia();
        if (dialog.getPoblacio() != null) {
            pane.getCmbPoblacio().setValue(dialog.getPoblacio());
        }
    }
}