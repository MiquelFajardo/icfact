package cat.informaticassa.icfact.ui.main.pagines.client.controller;

import cat.informaticassa.icfact.client.service.BuscarClientsService;
import cat.informaticassa.icfact.ui.main.pagines.client.PaginaClients;
import lombok.Getter;

@Getter
public class ClientController {
    private final PaginaClients pagina;
    private final BuscarClientsService buscarClientsService = new BuscarClientsService();

    public ClientController(PaginaClients pagina) {
        this.pagina = pagina;
        new ClientEvents(this);
        carregarActius();
    }

    public void carregarActius() {
        pagina.getTaula().getClients().setAll(buscarClientsService.buscarTots());
    }

    public void buscar(String text, boolean actius, boolean inactius) {
        pagina.getTaula().getClients().setAll(buscarClientsService.buscar(text, actius, inactius));
    }
}