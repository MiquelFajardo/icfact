package cat.informaticassa.icfact.ui.main.pagines.iva.table;

import cat.informaticassa.icfact.iva.model.Iva;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public final class IvaColumns {
    private IvaColumns() {
    }

    public static TableColumn<Iva, String> nom() {
        TableColumn<Iva, String> columna = new TableColumn<>("Nom");
        columna.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNom()));
        return columna;
    }

    public static TableColumn<Iva, String> percentatge() {
        TableColumn<Iva, String> columna = new TableColumn<>("IVA");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getPercentatge().stripTrailingZeros().toPlainString() + " %"));
        return columna;
    }

    public static TableColumn<Iva, String> actiu() {
        TableColumn<Iva, String> columna = new TableColumn<>("Estat");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().isActiu() ? "Actiu" : "Inactiu"));
        return columna;
    }
}