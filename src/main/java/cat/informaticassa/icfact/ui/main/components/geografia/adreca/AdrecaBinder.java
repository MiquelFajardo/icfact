package cat.informaticassa.icfact.ui.main.components.geografia.adreca;

import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.pais.BuscarPaisosService;
import cat.informaticassa.icfact.geografia.service.poblacio.BuscarPoblacionsPerProvinciaService;
import cat.informaticassa.icfact.geografia.service.provincia.BuscarProvinciesPerPaisService;

public class AdrecaBinder {

    private final AdrecaPane vista;

    private final BuscarPaisosService buscarPaisosService = new BuscarPaisosService();
    private final BuscarProvinciesPerPaisService buscarProvinciesPerPaisService = new BuscarProvinciesPerPaisService();
    private final BuscarPoblacionsPerProvinciaService buscarPoblacionsPerProvinciaService = new BuscarPoblacionsPerProvinciaService();

    public AdrecaBinder(AdrecaPane vista) {
        this.vista = vista;
        carregarPaisos();
    }

    public void carregar(Adreca adreca) {
        vista.getCmbPais().getItems().setAll(buscarPaisosService.executar());
        if (adreca.getPais() != null) {
            vista.getCmbProvincia().getItems().setAll(
                    buscarProvinciesPerPaisService.executar(adreca.getPais())
            );
        }
        if (adreca.getProvincia() != null) {
            vista.getCmbPoblacio().getItems().setAll(
                    buscarPoblacionsPerProvinciaService.executar(adreca.getProvincia())
            );
        }
        vista.mostrar(adreca);
    }

    public void actualitzar(Adreca adreca) {
        vista.actualitzar(adreca);
    }

    public void carregarPaisos() {
        vista.getCmbPais().getItems().setAll(buscarPaisosService.executar());
    }
}