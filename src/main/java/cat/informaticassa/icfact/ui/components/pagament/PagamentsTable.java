package cat.informaticassa.icfact.ui.components.pagament;

import cat.informaticassa.icfact.pagament.model.Pagament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("unchecked")
public class PagamentsTable extends TableView<Pagament> {
        private final ObservableList<Pagament> dades = FXCollections.observableArrayList();

    public PagamentsTable() {
        TableColumn<Pagament, LocalDate> data = new TableColumn<>("Data");
        data.setCellValueFactory(new PropertyValueFactory<>("dataPagament"));
        TableColumn<Pagament, BigDecimal> importPagat = new TableColumn<>("Import");
        importPagat.setCellValueFactory( new PropertyValueFactory<>("importPagat"));
        TableColumn<Pagament, String> referencia = new TableColumn<>("Referència");
        referencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        TableColumn<Pagament, String> observacions = new TableColumn<>("Observacions");
        observacions.setCellValueFactory(new PropertyValueFactory<>("observacions"));
        getColumns().addAll(data, importPagat, referencia, observacions);
        setItems(dades);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    public void mostrar(List<Pagament> pagaments) {
        dades.setAll(pagaments);
    }
}