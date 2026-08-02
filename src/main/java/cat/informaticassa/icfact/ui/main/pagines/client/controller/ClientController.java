package cat.informaticassa.icfact.ui.main.pagines.client.controller;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.service.BuscarClientsService;
import cat.informaticassa.icfact.ui.main.pagines.client.PaginaClients;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClientController {
    private final PaginaClients pagina;
    private final BuscarClientsService buscarClientsService = new BuscarClientsService();

    public ClientController(PaginaClients pagina) {
        this.pagina = pagina;
        carregarActius();
        new ClientEvents(this);
    }

    public void carregarActius() {
        pagina.getTaula().getClients().setAll(buscarClientsService.buscarTots());
    }

    public void carregarInactius() {
        pagina.getTaula().getClients().setAll(buscarClientsService.buscarInactius());
    }

    public void carregarTots() {
        List<Client> clients = new ArrayList<>();
        clients.addAll(buscarClientsService.buscarTots());
        clients.addAll(buscarClientsService.buscarInactius());
        pagina.getTaula().getClients().setAll(clients);
    }

    public void buscar(String text, boolean actius, boolean inactius) {
        pagina.getTaula().getClients().setAll(buscarClientsService.buscar(text, actius, inactius));
    }
}