package cat.informaticassa.icfact.ui.components.pressupost;

import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.service.BuscarIvaService;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.StringConverter;
import cat.informaticassa.icfact.pressupost.service.RecalcularLiniaPressupostService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import cat.informaticassa.icfact.producte.service.BuscarProducteService;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Setter
@Getter
public class PressupostLiniesTable extends TableView<LiniaPressupost> {
    private final ObservableList<LiniaPressupost> dades = FXCollections.observableArrayList();
    private final BuscarProducteService buscarProductesService = new BuscarProducteService();
    private final BuscarIvaService buscarIvaService = new BuscarIvaService();
    private PressupostPane pressupostPane;

    public PressupostLiniesTable() {
        setEditable(true);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        TableColumn<LiniaPressupost, String> colConcepte = createColConcepte();

        TableColumn<LiniaPressupost, String> colDescripcio = new TableColumn<>("Descripció");

        colDescripcio.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDescripcio()));

        colDescripcio.setCellFactory(TextFieldTableCell.forTableColumn());

        colDescripcio.setOnEditCommit(e ->
                e.getRowValue().setDescripcio(e.getNewValue()));

        TableColumn<LiniaPressupost, BigDecimal> colQuantitat = createColQuantitat();

        TableColumn<LiniaPressupost, BigDecimal> colPreu = createColPreu();

        TableColumn<LiniaPressupost, BigDecimal> colDte = createColDte();

        TableColumn<LiniaPressupost, Iva> colIva = new TableColumn<>("IVA");
        colIva.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getIva()));
        colIva.setCellFactory(ComboBoxTableCell.forTableColumn(
                        new StringConverter<>() {
                            @Override
                            public String toString(Iva iva) {
                                if (iva == null) {
                                    return "";
                                }
                                return iva.getPercentatge()
                                        .stripTrailingZeros()
                                        .toPlainString() + " %";
                            }
                            @Override
                            public Iva fromString(String string) {
                                return null;
                            }
                        },
                        buscarIvaService.buscarActius().toArray(new Iva[0]))
        );

        colIva.setOnEditCommit(e -> {
            LiniaPressupost linia = e.getRowValue();
            linia.setIva(e.getNewValue());
            new RecalcularLiniaPressupostService().executar(linia);
            refresh();
            if (pressupostPane != null) {
                pressupostPane.marcarModificat();
                pressupostPane.actualitzarTotals();
            }
        });

        TableColumn<LiniaPressupost, BigDecimal> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getTotal()));

        getColumns().clear();
        getColumns().add(colConcepte);
        getColumns().add(colQuantitat);
        getColumns().add(colPreu);
        getColumns().add(colDte);
        getColumns().add(colIva);
        getColumns().add(colTotal);
        setItems(dades);

        configurarTeclat();
        comprovarUltimaLinia();
    }

    private TableColumn<LiniaPressupost, BigDecimal> createColDte() {
        TableColumn<LiniaPressupost, BigDecimal> colDte = new TableColumn<>("Dte %");
        colDte.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getDte()));
        colDte.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colDte.setOnEditCommit(e -> {
            LiniaPressupost linia = e.getRowValue();
            linia.setDte(e.getNewValue());
            new RecalcularLiniaPressupostService().executar(linia);
            refresh();
            if (pressupostPane != null) {
                pressupostPane.marcarModificat();
                pressupostPane.actualitzarTotals();
            }
        });
        return colDte;
    }

    private TableColumn<LiniaPressupost, BigDecimal> createColPreu() {
        TableColumn<LiniaPressupost, BigDecimal> colPreu = new TableColumn<>("Preu");

        colPreu.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getPreu()));

        colPreu.setCellFactory(column -> new TextFieldTableCell<>(
                new BigDecimalStringConverter() {
                    @Override
                    public String toString(BigDecimal value) {
                        if (value == null) {
                            return "0.00";
                        }
                        return value.setScale(2, RoundingMode.HALF_UP).toPlainString();
                    }

                    @Override
                    public BigDecimal fromString(String value) {
                        if (value == null || value.isBlank()) {
                            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                        }
                        return new BigDecimal(value.replace(",", ".")).setScale(2, RoundingMode.HALF_UP);
                    }
                }
        ));

        colPreu.setOnEditCommit(e -> {
            LiniaPressupost linia = e.getRowValue();
            linia.setPreu(e.getNewValue().setScale(2, RoundingMode.HALF_UP));
            new RecalcularLiniaPressupostService().executar(linia);
            refresh();
            if (pressupostPane != null) {
                pressupostPane.marcarModificat();
                pressupostPane.actualitzarTotals();
            }
        });
        return colPreu;
    }

    private TableColumn<LiniaPressupost, BigDecimal> createColQuantitat() {
        TableColumn<LiniaPressupost, BigDecimal> colQuantitat = new TableColumn<>("Quantitat");

        colQuantitat.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getQuantitat()));

        colQuantitat.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));

        colQuantitat.setOnEditCommit(e -> {
            LiniaPressupost linia = e.getRowValue();
            linia.setQuantitat(e.getNewValue());
            new RecalcularLiniaPressupostService().executar(linia);
            refresh();
            if (pressupostPane != null) {
                pressupostPane.marcarModificat();
                pressupostPane.actualitzarTotals();
            }
        });
        return colQuantitat;
    }

    private static TableColumn<LiniaPressupost, String> createColConcepte() {
        TableColumn<LiniaPressupost, String> colConcepte = new TableColumn<>("Concepte");
        colConcepte.setPrefWidth(350);
        colConcepte.setCellValueFactory(c -> {
            LiniaPressupost linia = c.getValue();
            if (linia.getProducte() != null) {
                return new SimpleStringProperty(linia.getProducte().getNom());
            }
            return new SimpleStringProperty( linia.getDescripcio() == null ? "" : linia.getDescripcio());
        });

        colConcepte.setCellFactory(c -> new ProducteTableCell());
        return colConcepte;
    }

    public void mostrar(List<LiniaPressupost> linies) {
        dades.setAll(linies);
        comprovarUltimaLinia();
        refresh();
        if (pressupostPane != null) {
            pressupostPane.marcarModificat();
        }
    }

    public List<LiniaPressupost> obtenirLinies() {
        return dades.stream()
                .filter(linia ->
                        linia.getProducte() != null ||
                                (linia.getDescripcio() != null &&
                                        !linia.getDescripcio().isBlank()))
                .toList();
    }

    public void eliminarLinia(LiniaPressupost linia) {
        if (dades.size() == 1) {
            linia.setProducte(null);
            linia.setDescripcio("");
            linia.setQuantitat(BigDecimal.ZERO);
            linia.setPreu(BigDecimal.ZERO);
            linia.setSubtotal(BigDecimal.ZERO);
            linia.setTotal(BigDecimal.ZERO);
            refresh();
            if (pressupostPane != null) {
                pressupostPane.marcarModificat();
            }
            assert pressupostPane != null;
            pressupostPane.actualitzarTotals();
            return;
        }
        dades.remove(linia);
        comprovarUltimaLinia();
        refresh();
        if (pressupostPane != null) {
            pressupostPane.marcarModificat();
        }
        assert pressupostPane != null;
        pressupostPane.actualitzarTotals();
    }

    private void configurarTeclat() {
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                LiniaPressupost linia = getSelectionModel().getSelectedItem();
                if (linia != null) {
                    eliminarLinia(linia);
                }
            }
        });
    }

    public void comprovarUltimaLinia() {
        if (dades.isEmpty()) {
            dades.add(new LiniaPressupost());
            return;
        }
        LiniaPressupost ultima = dades.getLast();
        boolean buida = ultima.getProducte() == null && (ultima.getDescripcio() == null || ultima.getDescripcio().isBlank());
        if (!buida) {
            dades.add(new LiniaPressupost());
        }
    }
}