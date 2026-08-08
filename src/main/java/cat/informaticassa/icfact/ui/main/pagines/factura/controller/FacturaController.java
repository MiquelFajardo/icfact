package cat.informaticassa.icfact.ui.main.pagines.factura.controller;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.factura.service.BuscarFacturaService;
import cat.informaticassa.icfact.ui.main.pagines.factura.PaginaFactures;
import lombok.Getter;

@Getter
public class FacturaController {
    private final PaginaFactures pagina;
    private final BuscarFacturaService buscarFacturaService = new BuscarFacturaService();
    private Client clientFiltrat;

    public FacturaController(PaginaFactures pagina) {
        this.pagina = pagina;
        new FacturaEvents(this);
        carregarActives();
    }

    public void carregarActives() {
        buscar("", true, false, clientFiltrat);
    }

    public void carregarInactives() {
        buscar("", false, true, clientFiltrat);
    }

    public void carregarTotes() {
        buscar("", true, true, clientFiltrat);
    }

    public void mostrarFacturesClient(Client client) {
        clientFiltrat = client;
        pagina.getToolbar().getTxtBuscar().setText(client.getNom());
        pagina.getToolbar().getTxtBuscar().setDisable(true);
        buscar("", true, false, client);
    }

    public void treureFiltreClient() {
        this.clientFiltrat = null;
        pagina.getToolbar().treureClient();
        buscar("", true, false, null);

    }

    public void buscar(String text, boolean actives, boolean inactives, Client client) {
        if (text == null) {
            text = "";
        }
        pagina.getTaula().mostrar(buscarFacturaService.buscar(text.trim(), actives, inactives, client));
    }


}