package cat.informaticassa.icfact.ui.main.pagines.client.altaClient;

import lombok.Getter;

@Getter
public class ClientController {

    private final ClientPage pagina;

    public ClientController(ClientPage pagina) {
        this.pagina = pagina;

        new ClientEvents(this);
    }
}