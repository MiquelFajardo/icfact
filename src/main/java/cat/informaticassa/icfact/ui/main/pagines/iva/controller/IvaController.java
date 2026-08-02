package cat.informaticassa.icfact.ui.main.pagines.iva.controller;

import cat.informaticassa.icfact.iva.service.BuscarIvaService;
import cat.informaticassa.icfact.ui.main.pagines.iva.PaginaIVA;
import lombok.Getter;

@Getter
public class IvaController {
    private final PaginaIVA pagina;
    private final BuscarIvaService buscarIvaService = new BuscarIvaService();

    public IvaController(PaginaIVA pagina) {
        this.pagina = pagina;
        new IvaEvents(this);
        carregarActius();
    }

    public void carregarActius() {
        pagina.getTaula().mostrar(buscarIvaService.buscarActius());
    }

    public void carregarInactius() {
        pagina.getTaula().mostrar(buscarIvaService.buscarInactius());
    }

    public void carregarTots() {
        pagina.getTaula().mostrar(buscarIvaService.buscarTots());
    }

    public void buscar(String text, boolean actius, boolean inactius) {
        if (text == null) {
            text = "";
        }
        pagina.getTaula().mostrar(buscarIvaService.buscar(text, actius, inactius));
    }
}