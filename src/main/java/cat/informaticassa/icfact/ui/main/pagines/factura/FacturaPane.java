package cat.informaticassa.icfact.ui.main.pagines.factura;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.service.RecalcularFacturaService;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.ui.components.factura.FacturaBinder;
import cat.informaticassa.icfact.ui.components.factura.FacturaLiniesTable;
import cat.informaticassa.icfact.ui.components.factura.FacturaPaneController;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import cat.informaticassa.icfact.ui.util.searchable.SearchField;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;

import java.math.RoundingMode;

@Getter
@Setter
public class FacturaPane extends BorderPane {
    private DirtyTracker dirtyTracker;
    private final TextField txtNumero = new TextField();
    private final SearchField<Client> txtClient = new SearchField<>();
    private final Button botoNouClient = new Button("+");
    private final ComboBox<FormaPagament> cmbFormaPagament = new ComboBox<>();
    private final Button botoNovaFormaPagament =  new Button("+");
    private final Label lblEstat = new Label();
    private final DatePicker dpData = new DatePicker();
    private final TextArea txtObservacions = new TextArea();
    private final FacturaLiniesTable taulaLinies = new FacturaLiniesTable();
    private final TextField txtSubtotal = new TextField();
    private final TextField txtIva = new TextField();
    private final TextField txtTotal = new TextField();
    private final RecalcularFacturaService recalcularService = new RecalcularFacturaService();
    private final CheckBox chkActiu = new CheckBox("Actiu");
    private Factura factura;

    public FacturaPane() {
        construir();
        new FacturaPaneController(this);
        taulaLinies.setFacturaPane(this);
    }

    private void construir() {
        setPadding(new Insets(15));
        GridPane dades = new GridPane();
        dades.setHgap(10);
        dades.setVgap(10);
        int fila = 0;
        // Número
        txtNumero.setEditable(false);
        dades.add(new Label("Número"),0, fila);
        dades.add(txtNumero,1, fila);
        // Data
        dades.add(new Label("Data"),2, fila);
        dades.add(dpData,3, fila);
        fila++;
        // Client
        dades.add(new Label("Client"),0, fila);
        HBox clientBox = new HBox(5);
        clientBox.getChildren().addAll(txtClient, botoNouClient);
        dades.add( clientBox,1, fila);
        // Estat
        dades.add(new Label("Estat"),2, fila);
        HBox estatBox = new HBox(10);
        estatBox.getChildren().addAll(lblEstat, chkActiu);
        dades.add(estatBox, 3, fila);
        fila++;
        // Forma de pagament
        dades.add( new Label("Forma pagament"),0, fila);
        HBox formaBox = new HBox(5);
        formaBox.getChildren().addAll(cmbFormaPagament, botoNovaFormaPagament);
        dades.add(formaBox,1, fila);
        fila++;
        // Observacions
        dades.add( new Label("Observacions"),0, fila);
        dades.add(txtObservacions, 1, fila);
        GridPane.setColumnSpan(txtObservacions,3);
        txtObservacions.setPrefRowCount(3);
        VBox centre = new VBox(15);
        VBox.setVgrow(taulaLinies, Priority.ALWAYS);
        centre.getChildren().addAll( dades, taulaLinies);
        setCenter(centre);
        // Totals
        GridPane totals = new GridPane();
        totals.setHgap(10);
        totals.setVgap(10);
        txtSubtotal.setEditable(false);
        txtIva.setEditable(false);
        txtTotal.setEditable(false);
        totals.add( new Label("Subtotal"),0,0);
        totals.add(txtSubtotal,1,0);
        totals.add(new Label("IVA"),2,0);
        totals.add(txtIva,3,0);
        totals.add(new Label("Total"),4,0);
        totals.add(txtTotal,5,0);
        setBottom(totals);
    }

    public void mostrar(Factura factura) {
        this.factura = factura;
        new FacturaBinder(this).carregar(factura);
        actualitzarTotals();
    }

    public void actualitzarTotals() {
        if (taulaLinies.getItems().isEmpty()) {
            txtSubtotal.setText("0.00");
            txtIva.setText("0.00");
            txtTotal.setText("0.00");
            return;
        }
        Factura factura = new Factura();
        factura.setLinies(taulaLinies.obtenirLinies());
        recalcularService.executar(factura);
        txtSubtotal.setText(factura.getSubtotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
        txtIva.setText(factura.getIva().setScale(2, RoundingMode.HALF_UP).toPlainString());
        txtTotal.setText(factura.getTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

    public void registrarDirty(DirtyTracker dirtyTracker) {
        this.dirtyTracker = dirtyTracker;
        dpData.valueProperty().addListener((o, a, n) ->dirtyTracker.marcarModificat());
        txtClient.textProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        cmbFormaPagament.valueProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        txtObservacions.textProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        chkActiu.selectedProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        taulaLinies.getItems().addListener((javafx.collections.ListChangeListener<LiniaFactura>) c -> dirtyTracker.marcarModificat());
    }

    public void marcarModificat() {
        if (dirtyTracker != null) {
            dirtyTracker.marcarModificat();
        }
    }
}