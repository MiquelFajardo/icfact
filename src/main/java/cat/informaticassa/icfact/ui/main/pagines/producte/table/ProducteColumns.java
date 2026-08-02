package cat.informaticassa.icfact.ui.main.pagines.producte.table;

import cat.informaticassa.icfact.producte.model.Producte;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public final class ProducteColumns {
    private ProducteColumns() {
    }

    public static TableColumn<Producte, String> codi() {
        TableColumn<Producte, String> columna = new TableColumn<>("Codi");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getCodi())
        );
        return columna;
    }

    public static TableColumn<Producte, String> nom() {
        TableColumn<Producte, String> columna = new TableColumn<>("Nom");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getNom())
        );
        return columna;
    }

    public static TableColumn<Producte, String> preu() {
        TableColumn<Producte, String> columna = new TableColumn<>("Preu");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getPreu() == null
                                ? ""
                                : d.getValue().getPreu().stripTrailingZeros().toPlainString() + " €"
                )
        );
        return columna;
    }

    public static TableColumn<Producte, String> iva() {
        TableColumn<Producte, String> columna = new TableColumn<>("IVA");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getIva() == null
                                ? ""
                                : d.getValue().getIva().getPercentatge().stripTrailingZeros().toPlainString() + " %"
                )
        );
        return columna;
    }

    public static TableColumn<Producte, String> actiu() {
        TableColumn<Producte, String> columna = new TableColumn<>("Estat");
        columna.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().isActiu() ? "Actiu" : "Inactiu"
                )
        );
        return columna;
    }
}