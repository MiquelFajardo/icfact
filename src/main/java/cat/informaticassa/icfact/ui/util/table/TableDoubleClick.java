package cat.informaticassa.icfact.ui.util.table;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.util.function.Consumer;

public final class TableDoubleClick {

    private TableDoubleClick() {    }

    public static <T> void registrar( TableView<T> taula, Consumer<T> accio) {
        taula.setRowFactory(tv -> {
            TableRow<T> fila = new TableRow<>();
            fila.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !fila.isEmpty()) {
                    accio.accept(fila.getItem());
                }
            });
            return fila;
        });
    }
}