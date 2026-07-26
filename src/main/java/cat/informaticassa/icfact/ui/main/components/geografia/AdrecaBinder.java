package cat.informaticassa.icfact.ui.main.components.geografia;

import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.BuscarPaisosService;
import cat.informaticassa.icfact.geografia.service.BuscarPoblacionsPerProvinciaService;
import cat.informaticassa.icfact.geografia.service.BuscarProvinciesPerPaisService;

public class AdrecaBinder {
    private final AdrecaPane vista;
    private final BuscarPaisosService buscarPaisosService = new BuscarPaisosService();
    private final BuscarProvinciesPerPaisService buscarProvinciesPerPaisService = new BuscarProvinciesPerPaisService();
    private final BuscarPoblacionsPerProvinciaService buscarPoblacionsPerProvinciaService = new BuscarPoblacionsPerProvinciaService();

    public AdrecaBinder(AdrecaPane vista) {
        this.vista = vista;
    }

    public void carregar(Adreca adreca) {
        vista.getCmbPais().getItems().setAll(buscarPaisosService.executar());
        Poblacio poblacio = adreca.getPoblacio();
        Provincia provincia = poblacio.getProvincia();
        Pais pais = provincia.getPais();
        vista.getCmbProvincia().getItems().setAll(buscarProvinciesPerPaisService.executar(pais));
        vista.getCmbPoblacio().getItems().setAll(buscarPoblacionsPerProvinciaService.executar(provincia));
        vista.mostrar(adreca);
    }

    public void actualitzar(Adreca adreca) {
        vista.actualitzar(adreca);
    }
}