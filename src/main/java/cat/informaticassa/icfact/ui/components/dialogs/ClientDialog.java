package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.ui.components.client.ClientBinder;
import cat.informaticassa.icfact.ui.components.client.ClientEvents;
import cat.informaticassa.icfact.ui.components.client.ClientPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDialog extends DialogBase {
    private final ClientPane formulari = new ClientPane();
    private final ClientBinder binder = new ClientBinder(formulari);
    private Client client;

    public ClientDialog() {
        this(null);
    }

    public ClientDialog(Client client) {
        super(client == null ? "Nou client" : "Modificar client", 950,800);
        this.client = client;
        getRoot().setCenter(formulari);
        formulari.registrarDirty(getDirtyTracker());
        if (this.client != null) {
            binder.carregar(this.client);
            formulari.mostrarCampActiu(true);
        } else {
            formulari.mostrarCampActiu(false);
        }
        new ClientEvents(this);
    }

    public boolean esEdicio() {
        return client != null;
    }
}