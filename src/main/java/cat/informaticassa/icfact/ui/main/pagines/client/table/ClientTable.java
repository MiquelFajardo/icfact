package cat.informaticassa.icfact.ui.main.pagines.client.table;

import cat.informaticassa.icfact.client.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import lombok.Getter;

@Getter
public class ClientTable extends TableView<Client> {

    private final ObservableList<Client> clients =
            FXCollections.observableArrayList();

    public ClientTable() {

        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        getColumns().addAll(
                ClientColumns.nom(),
                ClientColumns.nif(),
                ClientColumns.telefon(),
                ClientColumns.email(),
                ClientColumns.poblacio()
        );

        setItems(clients);

        setPlaceholder(new javafx.scene.control.Label("No hi ha clients."));
    }
}