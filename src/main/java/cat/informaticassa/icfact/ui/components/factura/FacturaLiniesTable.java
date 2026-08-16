package cat.informaticassa.icfact.ui.components.factura;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.service.RecalcularFacturaService;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.service.BuscarIvaService;
import cat.informaticassa.icfact.ui.main.pagines.factura.FacturaPane;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
public class FacturaLiniesTable extends TableView<LiniaFactura> {
    private final ObservableList<LiniaFactura> dades = FXCollections.observableArrayList();
    private final BuscarIvaService buscarIvaService = new BuscarIvaService();
    private final RecalcularFacturaService recalcularService =  new RecalcularFacturaService();
    private FacturaPane facturaPane;

    public FacturaLiniesTable() {
        setEditable(true);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        TableColumn<LiniaFactura, String> colConcepte = createColConcepte();
        TableColumn<LiniaFactura, String> colDescripcio = new TableColumn<>("Descripció");
        colDescripcio.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getDescripcio() == null
                                ? ""
                                : c.getValue().getDescripcio()
                )
        );
        colDescripcio.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescripcio.setOnEditCommit(e -> {
            LiniaFactura linia = e.getRowValue();
            linia.setDescripcio(e.getNewValue());
            marcarModificat();
        });
        TableColumn<LiniaFactura, BigDecimal> colQuantitat = new TableColumn<>("Quantitat");
        colQuantitat.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getQuantitat()));
        colQuantitat.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colQuantitat.setOnEditCommit(e -> {
            LiniaFactura linia = e.getRowValue();
            linia.setQuantitat(e.getNewValue());
            recalcular(linia);
        });
        TableColumn<LiniaFactura, BigDecimal> colPreu = createColPreu();
        TableColumn<LiniaFactura, BigDecimal> colDte = new TableColumn<>("Dte %");
        colDte.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDte()));
        colDte.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colDte.setOnEditCommit(e -> {
            LiniaFactura linia = e.getRowValue();
            linia.setDte(e.getNewValue());
            recalcular(linia);
        });
        TableColumn<LiniaFactura, Iva> colIva = new TableColumn<>("IVA");
        colIva.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getIva()) );
        colIva.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<>() {
                            @Override
                            public String toString(Iva iva) {
                                if (iva == null) {
                                    return "";
                                }
                                return iva.getPercentatge().stripTrailingZeros().toPlainString() + " %";
                            }
                            @Override
                            public Iva fromString(String string) {
                                return null;
                            }
                        },
                        buscarIvaService.buscarActius().toArray(new Iva[0])
                )
        );

        colIva.setOnEditCommit(e -> {
            LiniaFactura linia = e.getRowValue();
            linia.setIva(e.getNewValue());
            recalcular(linia);
        });

        TableColumn<LiniaFactura, BigDecimal> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getTotal()));
        getColumns().clear();
        getColumns().addAll(List.of(colConcepte, colQuantitat, colPreu, colDte, colIva, colTotal));
        setItems(dades);
        configurarTeclat();
        comprovarUltimaLinia();
    }

    private TableColumn<LiniaFactura, BigDecimal> createColPreu() {
        TableColumn<LiniaFactura, BigDecimal> colPreu = new TableColumn<>("Preu");
        colPreu.setCellValueFactory(c ->
                new SimpleObjectProperty<>(c.getValue().getPreu()));
        colPreu.setCellFactory(column ->
                new TextFieldTableCell<>(
                        new StringConverter<>() {
                            @Override
                            public String toString(
                                    BigDecimal value) {
                                if (value == null) {
                                    return "0.00";
                                }
                                return value.setScale(2, RoundingMode.HALF_UP ).toPlainString();
                            }
                            @Override
                            public BigDecimal fromString(
                                    String value) {
                                if (value == null ||value.isBlank()) {
                                    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                                }
                                return new BigDecimal(value.replace(",", ".")).setScale(2, RoundingMode.HALF_UP);
                            }
                        }
                )
        );
        colPreu.setOnEditCommit(e -> {
            LiniaFactura linia = e.getRowValue();
            BigDecimal preu = e.getNewValue();
            if (preu == null) {
                preu = BigDecimal.ZERO;
            }
            linia.setPreu(preu.setScale(2,RoundingMode.HALF_UP));
            recalcular(linia);
        });
        return colPreu;
    }

    private static TableColumn<LiniaFactura, String> createColConcepte() {
        TableColumn<LiniaFactura, String> colConcepte =  new TableColumn<>("Concepte");
        colConcepte.setPrefWidth(350);
        colConcepte.setCellValueFactory(c -> {
            LiniaFactura linia = c.getValue();
            if (linia.getProducte() != null) {
                return new SimpleStringProperty(
                        linia.getProducte().getNom()
                );
            }
            return new SimpleStringProperty(
                    linia.getDescripcio() == null
                            ? ""
                            : linia.getDescripcio()
            );
        });
        colConcepte.setCellFactory(column -> new ProducteFacturaTableCell());
        return colConcepte;
    }

    public void mostrar(List<LiniaFactura> linies) {
        dades.setAll(linies);
        comprovarUltimaLinia();
        refresh();
    }

    public List<LiniaFactura> obtenirLinies() {
        return dades.stream().filter(linia ->
                        linia.getProducte() != null|| (linia.getDescripcio() != null && !linia.getDescripcio().isBlank())).toList();
    }

    public void eliminarLinia(LiniaFactura linia) {
        if (dades.size() == 1) {
            linia.setProducte(null);
            linia.setDescripcio("");
            linia.setQuantitat(BigDecimal.ZERO);
            linia.setPreu(BigDecimal.ZERO);
            linia.setDte(BigDecimal.ZERO);
            linia.setIva(null);
            linia.setSubtotal(BigDecimal.ZERO);
            linia.setTotal(BigDecimal.ZERO);
            refresh();
            marcarModificat();
            if (facturaPane != null) {
                facturaPane.actualitzarTotals();
            }
            return;
        }
        dades.remove(linia);
        comprovarUltimaLinia();
        refresh();
        marcarModificat();
        if (facturaPane != null) {
            facturaPane.actualitzarTotals();
        }
    }

    private void configurarTeclat() {
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                LiniaFactura linia = getSelectionModel().getSelectedItem();
                if (linia != null) {
                    eliminarLinia(linia);
                }
            }
        });
    }

    public void comprovarUltimaLinia() {
        if (dades.isEmpty()) {
            dades.add(new LiniaFactura());
            return;
        }
        LiniaFactura ultima = dades.getLast();
        boolean buida = ultima.getProducte() == null && (ultima.getDescripcio() == null || ultima.getDescripcio().isBlank());
        if (!buida) {
            dades.add(new LiniaFactura());
        }
    }

    private void recalcular(LiniaFactura linia) {
        if (linia.getQuantitat() == null) {
            linia.setQuantitat(BigDecimal.ZERO);
        }

        if (linia.getPreu() == null) {
            linia.setPreu(BigDecimal.ZERO);
        }

        if (linia.getDte() == null) {
            linia.setDte(BigDecimal.ZERO);
        }

        if (linia.getIva() == null) {
            refresh();
            marcarModificat();
            return;
        }
        Factura factura = new Factura();
        factura.setLinies(List.of(linia));
        recalcularService.executar(factura);
        refresh();
        marcarModificat();
        if (facturaPane != null) {
            facturaPane.actualitzarTotals();
        }
    }

    private void marcarModificat() {
        if (facturaPane != null) {
            facturaPane.marcarModificat();
        }
    }
}