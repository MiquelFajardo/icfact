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
import cat.informaticassa.icfact.ui.util.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdrecaEvents {
    private static final Logger logger = LoggerFactory.getLogger(AdrecaEvents.class);
    private final AdrecaPane vista;
    private final BuscarPaisosService buscarPaisosService = new BuscarPaisosService();
    private final BuscarProvinciesPerPaisService buscarProvinciesPerPaisService = new BuscarProvinciesPerPaisService();
    private final BuscarPoblacionsPerProvinciaService buscarPoblacionsPerProvinciaService = new BuscarPoblacionsPerProvinciaService();
    private final boolean carregant = false;

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
        try {
            vista.getCmbProvincia().getItems().setAll( buscarProvinciesPerPaisService.executar(pais));
        } catch (Exception e) {
            logger.error("Error carregant les províncies del país '{}'.", pais.getNom(), e);
            Alerta.error("No s'han pogut carregar les províncies.");
        }
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
        try {
            vista.getCmbPoblacio().getItems().setAll(buscarPoblacionsPerProvinciaService.executar(provincia));
        } catch (Exception e) {
            logger.error("Error carregant les poblacions de la província '{}'.", provincia.getNom(), e);
            Alerta.error("No s'han pogut carregar les poblacions.");
        }
    }

    private void nouPais() {
        try {
            PaisDialog dialog = new PaisDialog();
            dialog.showAndWait();
            Pais pais = dialog.getPais();
            if (pais == null) {
                return;
            }
            vista.getCmbPais().getItems().setAll(buscarPaisosService.executar());
            vista.getCmbPais().setValue(pais);
        } catch (Exception e) {
            logger.error("Error creant o carregant un nou país.", e);
            Alerta.error("No s'ha pogut crear el país.");
        }
    }

    private void novaProvincia() {
        Pais pais = vista.getCmbPais().getValue();
        if (pais == null) {
            Alerta.error("Primer has de seleccionar un país.");
            return;
        }
        try {
            ProvinciaDialog dialog = new ProvinciaDialog(pais);
            dialog.showAndWait();
            Provincia provincia = dialog.getProvincia();
            if (provincia == null) {
                return;
            }
            vista.getCmbProvincia().getItems().setAll(buscarProvinciesPerPaisService.executar(pais));
            vista.getCmbProvincia().setValue(provincia);
        } catch (Exception e) {
            logger.error("Error creant o carregant una nova província del país '{}'.", pais.getNom(), e);
            Alerta.error("No s'ha pogut crear la província.");
        }
    }

    private void novaPoblacio() {
        Provincia provincia = vista.getCmbProvincia().getValue();
        if (provincia == null) {
            Alerta.error("Primer has de seleccionar una província.");
            return;
        }
        try {
            PoblacioDialog dialog = new PoblacioDialog(provincia);
            dialog.showAndWait();
            Poblacio poblacio = dialog.getPoblacio();
            if (poblacio == null) {
                return;
            }
            vista.getCmbPoblacio().getItems().setAll(buscarPoblacionsPerProvinciaService.executar(provincia));
            vista.getCmbPoblacio().setValue(poblacio);
        } catch (Exception e) {
            logger.error("Error creant o carregant una nova població de la província '{}'.", provincia.getNom(), e);
            Alerta.error("No s'ha pogut crear la població.");
        }
    }
}