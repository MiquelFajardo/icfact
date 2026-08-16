package cat.informaticassa.icfact.ui.main.pagines.producte.controller;

import cat.informaticassa.icfact.producte.service.BuscarProducteService;
import cat.informaticassa.icfact.ui.main.pagines.producte.PaginaProductes;
import lombok.Getter;

@Getter
public class ProducteController {
    private final PaginaProductes pagina;

    private final BuscarProducteService buscarProductesService = new BuscarProducteService();
    public ProducteController(PaginaProductes pagina) {
        this.pagina = pagina;
        new ProducteEvents(this);
        carregarActius();
    }

    public void refrescar() {
        carregarActius();
    }

    public void carregarActius() {
        pagina.getTaula().mostrar(buscarProductesService.buscarActius());
    }

    public void buscar(String text, boolean actius, boolean inactius) {
        if (text == null) {
            text = "";
        }
        pagina.getTaula().mostrar(buscarProductesService.buscar(text, actius, inactius));
    }
}