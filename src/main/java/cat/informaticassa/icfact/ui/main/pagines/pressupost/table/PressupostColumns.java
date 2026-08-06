package cat.informaticassa.icfact.ui.main.pagines.pressupost.table;

import cat.informaticassa.icfact.pressupost.model.Pressupost;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import java.time.format.DateTimeFormatter;

public class PressupostColumns {
    private static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static TableColumn<Pressupost, String> columnaNumero() {
        TableColumn<Pressupost, String> columna = new TableColumn<>("Número");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getNumero()));
        columna.setPrefWidth(120);
        return columna;
    }

    public static TableColumn<Pressupost, String> columnaData() {
        TableColumn<Pressupost, String> columna = new TableColumn<>("Data");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getData() == null
                                ? ""
                                : d.getValue().getData().format(FORMAT_DATA)
                ));
        columna.setPrefWidth(110);
        return columna;
    }

    public static TableColumn<Pressupost, String> columnaClient() {
        TableColumn<Pressupost, String> columna = new TableColumn<>("Client");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getClient() == null
                                ? ""
                                : d.getValue().getClient().getNom()
                ));
        columna.setPrefWidth(280);
        return columna;
    }

    public static TableColumn<Pressupost, String> columnaEstat() {
        TableColumn<Pressupost, String> columna = new TableColumn<>("Estat");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getEstat() == null
                                ? ""
                                : d.getValue().getEstat().name()
                ));
        columna.setPrefWidth(140);
        return columna;
    }

    public static TableColumn<Pressupost, String> columnaTotal() {
        TableColumn<Pressupost, String> columna = new TableColumn<>("Total");
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

    public static TableColumn<Pressupost, String> columnaFormaPagament() {
        TableColumn<Pressupost, String> columna = new TableColumn<>("Forma pagament");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getFormaPagament() == null
                                ? ""
                                : d.getValue().getFormaPagament().getNom()
                ));
        columna.setPrefWidth(180);
        return columna;
    }
}