package cat.informaticassa.icfact.ui.components.geografia.adreca;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.pais.BuscarPaisosService;
import cat.informaticassa.icfact.geografia.service.poblacio.BuscarPoblacionsPerProvinciaService;
import cat.informaticassa.icfact.geografia.service.provincia.BuscarProvinciesPerPaisService;
import cat.informaticassa.icfact.ui.components.dialogs.PaisDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PoblacioDialog;
import cat.informaticassa.icfact.ui.components.dialogs.ProvinciaDialog;

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
        pane.getCmbPoblacio().setDisable(true);
        if (pais == null) {
            pane.getCmbProvincia().setDisable(true);
            return;
        }
        pane.getCmbProvincia().getItems().setAll(buscarProvinciesService.executar(pais));
        pane.getCmbProvincia().setDisable(false);
    }

    public void canviProvincia() {
        Provincia provincia = pane.getCmbProvincia().getValue();
        pane.getCmbPoblacio().getItems().clear();
        pane.getCmbPoblacio().setValue(null);
        if (provincia == null) {
            pane.getCmbPoblacio().setDisable(true);
            return;
        }
        pane.getCmbPoblacio().getItems().setAll(buscarPoblacionsService.executar(provincia));
        pane.getCmbPoblacio().setDisable(false);
    }

    private void nouPais() {
        PaisDialog dialog = new PaisDialog();
        dialog.initOwner(pane.getScene().getWindow());
        dialog.showAndWait();
        Pais paisCreat = dialog.getPais();
        if (paisCreat == null) {
            return;
        }
        carregarPaisos();
        pane.getCmbPais().getItems().stream().filter(pais ->pais.getId().equals(paisCreat.getId())).findFirst().ifPresent(pais -> {
                    pane.getCmbPais().setValue(pais);
                    canviPais(); });
    }
    private void novaProvincia() {
        Pais pais = pane.getCmbPais().getValue();
        if (pais == null) {
            return;
        }
        ProvinciaDialog dialog = new ProvinciaDialog(pais);
        dialog.initOwner(pane.getScene().getWindow());
        dialog.showAndWait();
        Provincia provinciaCreada = dialog.getProvincia();
        if (provinciaCreada == null) {
            return;
        }
        canviPais();
        pane.getCmbProvincia().getItems().stream().filter(provincia -> provincia.getId().equals(provinciaCreada.getId())).findFirst()
                .ifPresent(provincia -> {
                    pane.getCmbProvincia().setValue(provincia);
                    canviProvincia();
                });
    }

    private void novaPoblacio() {
        Provincia provincia = pane.getCmbProvincia().getValue();
        if (provincia == null) {
            return;
        }
        PoblacioDialog dialog = new PoblacioDialog(provincia);
        dialog.initOwner(pane.getScene().getWindow());
        dialog.showAndWait();
        Poblacio poblacioCreada = dialog.getPoblacio();
        if (poblacioCreada == null) {
            return;
        }
        canviProvincia();
        pane.getCmbPoblacio().getItems().stream().filter(poblacio -> poblacio.getId().equals(poblacioCreada.getId())).findFirst()
                .ifPresent(pane.getCmbPoblacio()::setValue);
    }
}