package cat.informaticassa.icfact.ui.main.pagines.formaPagament.table;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
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

public class FormaPagamentTable extends TableView<FormaPagament> {
    private final ObservableList<FormaPagament> dades = FXCollections.observableArrayList();
    @Setter
    private Consumer<FormaPagament> onModificar;

    public FormaPagamentTable() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        getColumns().addAll(
                FormaPagamentColumns.nom(),
                FormaPagamentColumns.descripcio(),
                FormaPagamentColumns.mostrarIban(),
                FormaPagamentColumns.actiu()
        );
        setItems(dades);
        setRowFactory(tv -> {
            TableRow<FormaPagament> row = new TableRow<>();
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

    public void mostrar(List<FormaPagament> formesPagament) {
        dades.setAll(formesPagament);
    }
}