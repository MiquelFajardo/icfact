package cat.informaticassa.icfact.ui.components.pagament;

import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class PagamentPane extends GridPane {
    private final Label lblTotal = new Label("0,00 €");
    private final Label lblPagat = new Label("0,00 €");
    private final Label lblResta = new Label("0,00 €");
    private final TextField txtImport = new TextField();
    private final DatePicker dpData = new DatePicker(LocalDate.now());
    private final TextField txtReferencia = new TextField();
    private final TextArea txtObservacions = new TextArea();

    public PagamentPane() {
        setHgap(15);
        setVgap(15);
        setPadding(new Insets(10));

        int fila = 0;

        add(new FormLabel("Total"), 0, fila);
        add(lblTotal, 1, fila++);

        add(new FormLabel("Pagat"), 0, fila);
        add(lblPagat, 1, fila++);

        add(new FormLabel("Resta"), 0, fila);
        add(lblResta, 1, fila++);

        add(new FormLabel("Import"), 0, fila);
        add(txtImport, 1, fila++);

        add(new FormLabel("Data"), 0, fila);
        add(dpData, 1, fila++);

        add(new FormLabel("Referència"), 0, fila);
        add(txtReferencia, 1, fila++);

        add(new FormLabel("Observacions"), 0, fila);
        add(txtObservacions, 1, fila);

        txtImport.setMaxWidth(Double.MAX_VALUE);
        dpData.setMaxWidth(Double.MAX_VALUE);
        txtReferencia.setMaxWidth(Double.MAX_VALUE);
        txtObservacions.setMaxWidth(Double.MAX_VALUE);

        txtObservacions.setPrefRowCount(4);
        txtObservacions.setWrapText(true);

        GridPane.setHgrow(txtImport, Priority.ALWAYS);
        GridPane.setHgrow(txtReferencia, Priority.ALWAYS);
        GridPane.setHgrow(txtObservacions, Priority.ALWAYS);
    }

    public void mostrarResum(
            BigDecimal total,
            BigDecimal pagat
    ) {
        BigDecimal resta = total.subtract(pagat);
        lblTotal.setText(format(total));
        lblPagat.setText(format(pagat));
        lblResta.setText(format(resta));
        txtImport.setText(resta.max(BigDecimal.ZERO).toPlainString());
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(tracker, txtImport, dpData, txtReferencia, txtObservacions);
    }

    private String format(BigDecimal valor) {
        return String.format(java.util.Locale.forLanguageTag("ca-ES"),"%.2f €", valor);
    }
}