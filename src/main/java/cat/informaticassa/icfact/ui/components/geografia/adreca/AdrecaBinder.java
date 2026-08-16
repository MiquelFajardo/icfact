package cat.informaticassa.icfact.ui.components.geografia.adreca;

import cat.informaticassa.icfact.geografia.model.Adreca;
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
        vista.mostrar(adreca);
        if (adreca.getPais() != null) {
            vista.getCmbPais().getItems().stream().filter(pais -> pais.getId().equals(adreca.getPais().getId()))
                    .findFirst()
                    .ifPresent(pais -> {
                        vista.getCmbPais().setValue(pais);
                        vista.getController().canviPais();
                    });
        }

        if (adreca.getProvincia() != null) {
            vista.getCmbProvincia().getItems().stream()
                    .filter(provincia -> provincia.getId().equals(adreca.getProvincia().getId()))
                    .findFirst()
                    .ifPresent(provincia -> {
                        vista.getCmbProvincia().setValue(provincia);
                        vista.getController().canviProvincia();
                    });
        }

        if (adreca.getPoblacio() != null) {
            vista.getCmbPoblacio().getItems().stream()
                    .filter(poblacio -> poblacio.getId().equals(adreca.getPoblacio().getId()))
                    .findFirst()
                    .ifPresent(vista.getCmbPoblacio()::setValue);
        }
    }

    public void actualitzar(Adreca adreca) {
        vista.actualitzar(adreca);
    }

    public void carregarPaisos() {
        vista.getCmbPais().getItems().setAll(buscarPaisosService.executar());
    }
}