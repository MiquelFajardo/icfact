package cat.informaticassa.icfact.ui.main.pagines.client.table;

import cat.informaticassa.icfact.client.model.Client;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;
import java.util.function.Consumer;

@Getter
public class ClientTable extends TableView<Client> {
    private final ObservableList<Client> clients = FXCollections.observableArrayList();
    @Setter
    private Consumer<Client> onModificar;
    @Setter
    private Consumer<Client> onPressupostos;
    @Setter
    private Consumer<Client> onFactures;

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
        setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            ContextMenu menu = new ContextMenu();
            MenuItem pressupostos = new MenuItem("📄 Pressupostos");
            MenuItem factures = new MenuItem("💶 Factures");
            MenuItem modificar = new MenuItem("✏ Modificar");

            menu.getItems().addAll(
                    pressupostos,
                    factures,
                    modificar
            );

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu)
            );

            modificar.setOnAction(e -> {
                if (onModificar != null) {
                    onModificar.accept(row.getItem());
                }
            });

            pressupostos.setOnAction(e -> {
                if (onPressupostos != null) {
                    onPressupostos.accept(row.getItem());
                }
            });

            factures.setOnAction(e -> {
                if (onFactures != null) {
                    onFactures.accept(row.getItem());
                }
            });

            return row;
        });
    }
}