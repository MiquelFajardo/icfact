package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.pagament.PagamentsTable;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class PagamentsDialog extends DialogBase {
    private final PagamentsTable taula = new PagamentsTable();
    private final PagamentRepository pagamentRepository = new PagamentRepository();
    private final Pressupost pressupost;
    private final Factura factura;
    private final Label lblTotal = new Label("0,00 €");
    private final Label lblPagat = new Label("0,00 €");
    private final Label lblPendent = new Label("0,00 €");
    private final BotoPrimari botoAfegirPagament = new BotoPrimari("💶 Afegir pagament");

    public PagamentsDialog(Pressupost pressupost) {
        super("Pagaments del pressupost", 900, 600);
        if (pressupost == null) {
            throw new IllegalArgumentException("El pressupost no pot ser nul.");
        }
        this.pressupost = pressupost;
        this.factura = null;
        inicialitzar();
        carregar();
    }

    public PagamentsDialog(Factura factura) {
        super("Pagaments de la factura", 900, 600);
        if (factura == null) {
            throw new IllegalArgumentException("La factura no pot ser nul·la.");
        }
        this.pressupost = null;
        this.factura = factura;
        inicialitzar();
        carregar();
    }

    private void inicialitzar() {
        GridPane resum = new GridPane();

        resum.setHgap(30);
        resum.setVgap(8);
        resum.setPadding(new Insets(0, 0, 15, 0));

        resum.add(new FormLabel("Total"), 0, 0);
        resum.add(lblTotal, 1, 0);

        resum.add(new FormLabel("Pagat"), 2, 0);
        resum.add(lblPagat, 3, 0);

        resum.add(new FormLabel("Pendent"), 4, 0);
        resum.add(lblPendent, 5, 0);

        HBox boto = new HBox(botoAfegirPagament);
        boto.setPadding(new Insets(0, 0, 15, 0));

        VBox superior = new VBox(10, resum, boto);

        getRoot().setTop(superior);
        getRoot().setCenter(taula);
        getBotoGuardar().setVisible(false);
        getBotoGuardar().setManaged(false);
        botoAfegirPagament.setOnAction(e -> afegirPagament());
    }

    private void carregar() {
        List<Pagament> pagaments;
        BigDecimal total;
        BigDecimal pagat;

        if (pressupost != null) {
            total = pressupost.getTotal() == null ? BigDecimal.ZERO : pressupost.getTotal();
            pagat = pagamentRepository.calcularImportPagat(pressupost);
            pagaments = pagamentRepository.buscarPerPressupost(pressupost);
        } else {
            total = factura.getTotal() == null ? BigDecimal.ZERO : factura.getTotal();
            pagat = pagamentRepository.calcularImportPagat(factura);
            pagaments = pagamentRepository.buscarPerFactura(factura);
        }
        BigDecimal pendent = total.subtract(pagat);
        lblTotal.setText(format(total));
        lblPagat.setText(format(pagat));
        lblPendent.setText(format(pendent.max(BigDecimal.ZERO)));
        if (pressupost != null && pressupost.getEstat() == EstatPressupost.FACTURAT) {
            botoAfegirPagament.setDisable(true);
        } else {
            botoAfegirPagament.setDisable(pendent.compareTo(BigDecimal.ZERO) <= 0);
        }
        taula.mostrar(pagaments);
    }

    private void afegirPagament() {
        Stage stage = this;
        PagamentDialog dialog;
        if (pressupost != null) {
            dialog = new PagamentDialog(pressupost);
        } else {
            dialog = new PagamentDialog(factura);
        }
        dialog.initOwner(stage);
        dialog.showAndWait();
        carregar();
    }

    private String format(BigDecimal valor) {
        return String.format(java.util.Locale.forLanguageTag("ca-ES"),"%.2f €", valor);
    }
}