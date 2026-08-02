package cat.informaticassa.icfact.ui.main.navigation;

import cat.informaticassa.icfact.client.model.Client;

public interface ClientNavigator {

    void obrirFitxa(Client client);

    void nouClient();

    void refrescar();
}