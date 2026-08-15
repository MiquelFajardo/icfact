package cat.informaticassa.icfact.ui.main.pagines.producte.table;

import cat.informaticassa.icfact.producte.model.Producte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import lombok.Setter;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class ProducteTable extends TableView<Producte> {
    private final ObservableList<Producte> dades = FXCollections.observableArrayList();
    @Setter
    private Consumer<Producte> onModificar;

    public ProducteTable() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        getColumns().addAll(
                ProducteColumns.codi(),
                ProducteColumns.nom(),
                ProducteColumns.preu(),
                ProducteColumns.iva(),
                ProducteColumns.actiu()
        );

        setItems(dades);
        setRowFactory(tv -> {
            TableRow<Producte> row = new TableRow<>();
            MenuItem modificar = new MenuItem("✏ Modificar");
            modificar.setOnAction(e -> {
                if (onModificar != null && row.getItem() != null) {
                    onModificar.accept(row.getItem());
                }
            });

            ContextMenu menu = new ContextMenu(modificar);
            row.emptyProperty().addListener((obs, oldValue, empty) ->
                    row.setContextMenu(empty ? null : menu));
            row.setOnMouseClicked(e -> {
                if (e.getButton() != MouseButton.PRIMARY) {
                    return;
                }
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    if (onModificar != null) {
                        onModificar.accept(row.getItem());
                    }
                }
            });
            return row;
        });
    }

    public void mostrar(List<Producte> productes) {
        dades.setAll(productes);
    }
}