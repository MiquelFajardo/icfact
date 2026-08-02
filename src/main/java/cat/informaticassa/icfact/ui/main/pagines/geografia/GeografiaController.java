package cat.informaticassa.icfact.ui.main.pagines.geografia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.pais.BuscarPaisosService;
import cat.informaticassa.icfact.geografia.service.provincia.BuscarProvinciesPerPaisService;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.service.poblacio.BuscarPoblacionsPerProvinciaService;
import lombok.Getter;

@Getter
public class GeografiaController {
    private final PaginaGeografia pagina;
    private final BuscarPaisosService buscarPaisosService = new BuscarPaisosService();
    private final BuscarProvinciesPerPaisService buscarProvinciesPerPaisService = new BuscarProvinciesPerPaisService();
    private Pais paisSeleccionat;
    private Provincia provinciaSeleccionada;
    private final BuscarPoblacionsPerProvinciaService buscarPoblacionsPerProvinciaService = new BuscarPoblacionsPerProvinciaService();
    private Poblacio poblacioSeleccionada;

    public GeografiaController(PaginaGeografia pagina) {
        this.pagina = pagina;
        carregarPaisos();
        new GeografiaEvents(this);
    }

    public void carregarPaisos() {
        pagina.getCmbPais().getItems().clear();
        buscarPaisosService.executar().forEach(pais -> pagina.getCmbPais().getItems().add(pais.getNom()));
        paisSeleccionat = null;
        provinciaSeleccionada = null;
        pagina.getBotoModificarPais().setDisable(true);
        pagina.getCmbProvincia().setDisable(true);
        pagina.getBotoNovaProvincia().setDisable(true);
        pagina.getBotoModificarProvincia().setDisable(true);
        pagina.getCmbPoblacio().setDisable(true);
        pagina.getBotoNovaPoblacio().setDisable(true);
        pagina.getBotoModificarPoblacio().setDisable(true);
    }

    public void seleccionarPais() {
        String nom = pagina.getCmbPais()
                .getSelectionModel()
                .getSelectedItem();
        paisSeleccionat = buscarPaisosService.executar()
                .stream()
                .filter(p -> p.getNom().equals(nom))
                .findFirst()
                .orElse(null);
    }

    public void carregarProvincies() {
        pagina.getCmbProvincia().getItems().clear();
        provinciaSeleccionada = null;
        if (paisSeleccionat == null) {
            return;
        }
        buscarProvinciesPerPaisService.executar(paisSeleccionat)
                .forEach(provincia ->
                        pagina.getCmbProvincia()
                                .getItems()
                                .add(provincia.getNom())
                );
    }

    public void seleccionarProvincia() {
        String nom = pagina.getCmbProvincia()
                .getSelectionModel()
                .getSelectedItem();
        provinciaSeleccionada = buscarProvinciesPerPaisService.executar(paisSeleccionat)
                .stream()
                .filter(p -> p.getNom().equals(nom))
                .findFirst()
                .orElse(null);
    }

    public void carregarPoblacions() {
        pagina.getCmbPoblacio().getItems().clear();
        poblacioSeleccionada = null;
        if (provinciaSeleccionada == null) {
            return;
        }
        buscarPoblacionsPerProvinciaService.executar(provinciaSeleccionada)
                .forEach(poblacio ->
                        pagina.getCmbPoblacio()
                                .getItems()
                                .add(poblacio.getNom())
                );
    }

    public void seleccionarPoblacio() {
        String nom = pagina.getCmbPoblacio()
                .getSelectionModel()
                .getSelectedItem();
        poblacioSeleccionada = buscarPoblacionsPerProvinciaService
                .executar(provinciaSeleccionada)
                .stream()
                .filter(p -> p.getNom().equals(nom))
                .findFirst()
                .orElse(null);
    }


}