package cat.informaticassa.icfact.ui.main.components.empresa;

import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.main.components.color.ColorPane;
import cat.informaticassa.icfact.ui.main.components.geografia.adreca.AdrecaBinder;
import cat.informaticassa.icfact.ui.main.components.geografia.adreca.AdrecaEvents;
import cat.informaticassa.icfact.ui.main.components.geografia.adreca.AdrecaPane;
import cat.informaticassa.icfact.ui.main.components.pdf.PeuPdfPane;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import cat.informaticassa.icfact.ui.components.Card;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormulariEmpresa extends GridPane {
    private final DirtyTracker dirtyTracker = new DirtyTracker();
    private final DadesEmpresaPane dadesEmpresa = new DadesEmpresaPane();
    private final AdrecaPane adreca = new AdrecaPane();
    private final ColorPane color = new ColorPane();
    private final PeuPdfPane peuPdf = new PeuPdfPane();
    private final EmpresaBinder empresaBinder = new EmpresaBinder(dadesEmpresa, color, peuPdf);
    private final AdrecaBinder adrecaBinder = new AdrecaBinder(adreca);
    private final AdrecaEvents adrecaEvents = new AdrecaEvents(adreca);
    private BotoPrimari botoGuardar;

    public FormulariEmpresa() {
        setHgap(25);
        setVgap(25);
        ColumnConstraints esquerra = new ColumnConstraints();
        esquerra.setPercentWidth(50);
        ColumnConstraints dreta = new ColumnConstraints();
        dreta.setPercentWidth(50);
        getColumnConstraints().addAll(esquerra, dreta);
        Card cardEmpresa = new Card("Dades de l'empresa", dadesEmpresa);
        Card cardAdreca = new Card("Adreça", adreca);
        VBox documentacio = new VBox(20);
        documentacio.getChildren().addAll(color, peuPdf);
        Card cardDocumentacio = new Card("Documentació", documentacio);
        cardEmpresa.setMaxWidth(Double.MAX_VALUE);
        cardAdreca.setMaxWidth(Double.MAX_VALUE);
        cardDocumentacio.setMaxWidth(Double.MAX_VALUE);
        add(cardEmpresa,0,0);
        add(cardAdreca,1,0);
        add(cardDocumentacio,0,1,2,1);
        dadesEmpresa.registrarDirty(dirtyTracker);
        adreca.registrarDirty(dirtyTracker);
        color.registrarDirty(dirtyTracker);
        peuPdf.registrarDirty(dirtyTracker);
    }
}