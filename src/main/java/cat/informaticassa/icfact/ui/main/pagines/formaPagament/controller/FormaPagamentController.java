package cat.informaticassa.icfact.ui.main.pagines.formaPagament.controller;

import cat.informaticassa.icfact.formaPagament.service.BuscarFormesPagamentService;
import cat.informaticassa.icfact.ui.main.pagines.formaPagament.PaginaFormesPagament;
import lombok.Getter;

@Getter
public class FormaPagamentController {
    private final PaginaFormesPagament pagina;
    private final BuscarFormesPagamentService buscarFormesPagamentService = new BuscarFormesPagamentService();

    public FormaPagamentController(PaginaFormesPagament pagina) {
        this.pagina = pagina;
        new FormaPagamentEvents(this);
        carregarActius();
    }

    public void carregarActius() {
        pagina.getTaula().mostrar(buscarFormesPagamentService.buscarActius());
    }

    public void carregarInactius() {
        pagina.getTaula().mostrar(buscarFormesPagamentService.buscarInactius());
    }

    public void carregarTots() {
        pagina.getTaula().mostrar(buscarFormesPagamentService.buscarTots());
    }

    public void buscar(String text, boolean actius, boolean inactius) {
        if (text == null) {
            text = "";
        }
        pagina.getTaula().mostrar(buscarFormesPagamentService.buscar(text, actius, inactius)
        );
    }
}