package cat.informaticassa.icfact.ui.main.pagines.client;

import cat.informaticassa.icfact.ui.main.pagines.client.controller.ClientController;
import cat.informaticassa.icfact.ui.main.pagines.client.table.ClientTable;
import cat.informaticassa.icfact.ui.main.pagines.client.toolbar.ClientToolbar;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class PaginaClients extends BorderPane {

    private final ClientToolbar toolbar = new ClientToolbar();
    private final ClientTable taula = new ClientTable();

    public PaginaClients() {

        setTop(toolbar);
        setCenter(taula);
        new ClientController(this);
    }
}