package cat.informaticassa.icfact.ui.main.pagines.formaPagament.table;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public final class FormaPagamentColumns {
    private FormaPagamentColumns() {
    }

    public static TableColumn<FormaPagament, String> nom() {
        TableColumn<FormaPagament, String> columna = new TableColumn<>("Nom");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getNom())
        );
        return columna;
    }

    public static TableColumn<FormaPagament, String> descripcio() {
        TableColumn<FormaPagament, String> columna = new TableColumn<>("Descripció");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getDescripcio() == null
                                ? ""
                                : d.getValue().getDescripcio()
                )
        );
        return columna;
    }

    public static TableColumn<FormaPagament, String> mostrarIban() {
        TableColumn<FormaPagament, String> columna = new TableColumn<>("Mostrar IBAN");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        Boolean.TRUE.equals(d.getValue().getMostrarIban()) ? "Sí" : "No"
                )
        );
        return columna;
    }

    public static TableColumn<FormaPagament, String> actiu() {
        TableColumn<FormaPagament, String> columna = new TableColumn<>("Estat");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().isActiu() ? "Actiu" : "Inactiu"
                )
        );
        return columna;
    }
}