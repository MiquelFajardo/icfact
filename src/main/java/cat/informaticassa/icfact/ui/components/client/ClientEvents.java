package cat.informaticassa.icfact.ui.components.client;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.service.ActualitzarClientService;
import cat.informaticassa.icfact.client.service.GuardarClientService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.components.dialogs.ClientDialog;
import cat.informaticassa.icfact.ui.util.Alerta;

public class ClientEvents {
    private final ClientDialog dialog;
    private final ClientPane formulari;
    private final ClientBinder binder;
    private final GuardarClientService guardarClientService = new GuardarClientService();
    private final ActualitzarClientService actualitzarClientService = new ActualitzarClientService();

    public ClientEvents(ClientDialog dialog) {
        this.dialog = dialog;
        this.formulari = dialog.getFormulari();
        this.binder = new ClientBinder(formulari);
        dialog.getBotoGuardar().setOnAction(e -> guardar());
    }

    private void guardar() {
        try {
            if (dialog.esEdicio()) {
                Client client = dialog.getClient();
                binder.actualitzar(client);
                actualitzarClientService.executar(client);
                dialog.close();
            } else {
                Client client = new Client();
                binder.actualitzar(client);
                guardarClientService.executar(client);
                dialog.setClient(client);
                dialog.close();
            }
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Alerta.error("No s'ha pogut desar el client.");
        }
    }
}