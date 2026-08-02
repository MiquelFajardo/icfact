package cat.informaticassa.icfact.ui.main.pagines.iva.table;

import cat.informaticassa.icfact.iva.model.Iva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import lombok.Setter;

import java.util.List;
import java.util.function.Consumer;

public class IvaTable extends TableView<Iva> {
    private final ObservableList<Iva> dades = FXCollections.observableArrayList();
    @Setter
    private Consumer<Iva> onModificar;

    public IvaTable() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        getColumns().addAll(
                IvaColumns.nom(),
                IvaColumns.percentatge(),
                IvaColumns.actiu()
        );

        setItems(dades);
        setRowFactory(tv -> {
            TableRow<Iva> row = new TableRow<>();
            MenuItem modificar = new MenuItem("Modificar");
            modificar.setOnAction(e -> {
                if (onModificar != null && row.getItem() != null) {
                    onModificar.accept(row.getItem());
                }
            });
            ContextMenu menu = new ContextMenu(modificar);
            row.emptyProperty().addListener((obs, emptyOld, empty) -> {
                row.setContextMenu(empty ? null : menu);
            });
            return row;
        });
    }

    public void mostrar(List<Iva> iva) {
        dades.setAll(iva);
    }
}