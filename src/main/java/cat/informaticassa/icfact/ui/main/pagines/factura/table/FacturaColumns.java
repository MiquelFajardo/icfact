package cat.informaticassa.icfact.ui.main.pagines.factura.table;

import cat.informaticassa.icfact.factura.model.Factura;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

import java.time.format.DateTimeFormatter;

public class FacturaColumns {
    private static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static TableColumn<Factura, String> numero() {
        TableColumn<Factura, String> columna = new TableColumn<>("Número");
        columna.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNumero()));
        columna.setPrefWidth(120);
        return columna;
    }

    public static TableColumn<Factura, String> data() {
        TableColumn<Factura, String> columna = new TableColumn<>("Data");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getData() == null
                                ? ""
                                : d.getValue().getData().format(FORMAT_DATA)
                ));

        columna.setPrefWidth(110);
        return columna;
    }

    public static TableColumn<Factura, String> client() {
        TableColumn<Factura, String> columna = new TableColumn<>("Client");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getClient() == null
                                ? ""
                                : d.getValue().getClient().getNom()
                ));
        columna.setPrefWidth(280);
        return columna;
    }

    public static TableColumn<Factura, String> estat() {
        TableColumn<Factura, String> columna = new TableColumn<>("Estat");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getEstat() == null
                                ? ""
                                : d.getValue().getEstat().name()
                ));
        columna.setPrefWidth(140);
        return columna;
    }

    public static TableColumn<Factura, String> total() {
        TableColumn<Factura, String> columna = new TableColumn<>("Total");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getTotal() == null
                                ? ""
                                : d.getValue().getTotal().toPlainString() + " €"
                ));
        columna.setStyle("-fx-alignment:CENTER-RIGHT;");
        columna.setPrefWidth(120);
        return columna;
    }
}