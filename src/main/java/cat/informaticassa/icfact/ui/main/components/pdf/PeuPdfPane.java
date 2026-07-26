package cat.informaticassa.icfact.ui.main.components.pdf;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class PeuPdfPane extends GridPane {
    private final TextArea txtPeuPdf = new TextArea();

    public PeuPdfPane() {
        setHgap(15);
        setVgap(15);
        setMaxWidth(Double.MAX_VALUE);
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(c1, c2);
        txtPeuPdf.setPrefRowCount(4);
        txtPeuPdf.setWrapText(true);
        txtPeuPdf.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(txtPeuPdf, Priority.ALWAYS);
        add(new FormLabel("Peu PDF"), 0, 0);
        add(txtPeuPdf, 1, 0);
    }

    public void mostrar(Empresa empresa) {
        txtPeuPdf.setText(empresa.getPeuPdf());
    }

    public void actualitzar(Empresa empresa) {
        empresa.setPeuPdf(txtPeuPdf.getText());
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(tracker, txtPeuPdf);
    }
}