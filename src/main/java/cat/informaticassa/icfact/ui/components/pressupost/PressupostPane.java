package cat.informaticassa.icfact.ui.components.pressupost;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.service.RecalcularPressupostService;
import cat.informaticassa.icfact.ui.util.searchable.SearchField;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;

import java.math.RoundingMode;

@Getter
@Setter
public class PressupostPane extends BorderPane {
    private DirtyTracker dirtyTracker;
    private final TextField txtNumero = new TextField();
    private final SearchField<Client> txtClient = new SearchField<>();
    private final Button botoNouClient = new Button("+");
    private final ComboBox<FormaPagament> cmbFormaPagament = new ComboBox<>();
    private final Button botoNovaFormaPagament = new Button("+");
    private final Label lblEstat = new Label();
    private final DatePicker dpData = new DatePicker();
    private final TextArea txtObservacions = new TextArea();
    private final PressupostLiniesTable taulaLinies = new PressupostLiniesTable();
    private final TextField txtSubtotal = new TextField();
    private final TextField txtIva = new TextField();
    private final TextField txtTotal = new TextField();
    private final RecalcularPressupostService recalcularService = new RecalcularPressupostService();
    private final CheckBox chkActiu = new CheckBox("Actiu");
    private Pressupost pressupost;

    public PressupostPane() {
        construir();
        new PressupostPaneController(this);
        taulaLinies.setPressupostPane(this);
    }

    private void construir() {
        setPadding(new Insets(15));
        GridPane dades = new GridPane();
        dades.setHgap(10);
        dades.setVgap(10);
        int fila = 0;
        txtNumero.setEditable(false);
        dades.add(new Label("Número"), 0, fila);
        dades.add(txtNumero, 1, fila);
        dades.add(new Label("Data"), 2, fila);
        dades.add(dpData, 3, fila);
        fila++;
        dades.add(new Label("Client"), 0, fila);
        HBox clientBox = new HBox(5);
        clientBox.getChildren().addAll(txtClient, botoNouClient );
        dades.add(clientBox, 1, fila);
        dades.add(new Label("Estat"), 2, fila);
        HBox estatBox = new HBox(10);
        estatBox.getChildren().addAll(lblEstat, chkActiu);
        dades.add(estatBox, 3, fila);
        fila++;
        dades.add(new Label("Forma pagament"), 0, fila);
        HBox formaBox = new HBox(5);
        formaBox.getChildren().addAll(cmbFormaPagament, botoNovaFormaPagament);
        dades.add(formaBox, 1, fila);
        fila++;
        dades.add(new Label("Observacions"), 0, fila);
        dades.add(txtObservacions, 1, fila);
        GridPane.setColumnSpan(txtObservacions, 3);
        txtObservacions.setPrefRowCount(3);
        VBox centre = new VBox(15);
        VBox.setVgrow(taulaLinies, Priority.ALWAYS);
        centre.getChildren().addAll(dades,taulaLinies);
        setCenter(centre);
        GridPane totals = new GridPane();
        totals.setHgap(10);
        totals.setVgap(10);
        txtSubtotal.setEditable(false);
        txtIva.setEditable(false);
        txtTotal.setEditable(false);
        totals.add(new Label("Subtotal"), 0, 0);
        totals.add(txtSubtotal, 1, 0);
        totals.add(new Label("IVA"), 2, 0);
        totals.add(txtIva, 3, 0);
        totals.add(new Label("Total"), 4, 0);
        totals.add(txtTotal, 5, 0);
        setBottom(totals);
    }

    public void mostrar(Pressupost pressupost) {
        this.pressupost = pressupost;
        new PressupostBinder(this).carregar(pressupost);
        actualitzarTotals();
    }

    public void actualitzarTotals() {
        if (taulaLinies.getItems().isEmpty()) {
            txtSubtotal.setText("0.00");
            txtIva.setText("0.00");
            txtTotal.setText("0.00");
            return;
        }
        Pressupost pressupost = new Pressupost();
        pressupost.setLinies(taulaLinies.obtenirLinies());
        recalcularService.executar(pressupost);
        txtSubtotal.setText(pressupost.getSubtotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
        txtIva.setText(pressupost.getIva().setScale(2, RoundingMode.HALF_UP).toPlainString());
        txtTotal.setText(pressupost.getTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

    public void registrarDirty(DirtyTracker dirtyTracker) {
        this.dirtyTracker = dirtyTracker;
        dpData.valueProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        txtClient.textProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        cmbFormaPagament.valueProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        chkActiu.selectedProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        txtObservacions.textProperty().addListener((o, a, n) -> dirtyTracker.marcarModificat());
        taulaLinies.getItems().addListener((javafx.collections.ListChangeListener<LiniaPressupost>) c ->dirtyTracker.marcarModificat());
    }

    public void marcarModificat() {
        if (dirtyTracker != null) {
            dirtyTracker.marcarModificat();
        }
    }
}