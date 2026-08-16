package cat.informaticassa.icfact.ui.main.pagines.pressupost.controller;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.pressupost.service.BuscarPressupostService;
import cat.informaticassa.icfact.ui.main.pagines.pressupost.PaginaPressupostos;
import lombok.Getter;

@Getter
public class PressupostController {
    private final PaginaPressupostos pagina;
    private final BuscarPressupostService buscarPressupostService = new BuscarPressupostService();
    private Client clientFiltrat;

    public PressupostController(PaginaPressupostos pagina) {
        this.pagina = pagina;
        new PressupostEvents(this);
        carregarActius();
    }

    public void carregarActius() {
        pagina.getTaula().mostrar(buscarPressupostService.buscarActius());
    }

    public void mostrarPressupostosClient(Client client) {
        this.clientFiltrat = client;
        pagina.getToolbar().mostrarClient(client);
        buscar("", true, false);
    }

    public void treureFiltreClient() {
        this.clientFiltrat = null;
        pagina.getToolbar().treureClient();
        buscar("", true, false);
    }

    public void buscar(String text, boolean actius, boolean inactius) {
        if (text == null) {
            text = "";
        }
        pagina.getTaula().mostrar(buscarPressupostService.buscar(text.trim(), actius, inactius, clientFiltrat));
    }
}