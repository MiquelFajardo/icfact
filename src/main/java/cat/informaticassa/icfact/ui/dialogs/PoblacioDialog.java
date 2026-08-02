package cat.informaticassa.icfact.ui.dialogs;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.main.components.geografia.poblacio.PoblacioEvents;
import cat.informaticassa.icfact.ui.main.components.geografia.poblacio.PoblacioPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoblacioDialog extends DialogBase {
    private final PoblacioPane formulari = new PoblacioPane();
    private Poblacio poblacio;
    private Provincia provincia;

    public PoblacioDialog(Provincia provincia) {
        this(provincia, null);
    }

    public PoblacioDialog(Provincia provincia, Poblacio poblacio) {
        super(poblacio == null ? "Nova població" : "Modificar població");
        this.provincia = provincia;
        this.poblacio = poblacio;
        getRoot().setCenter(formulari);
        if (poblacio != null) {
            formulari.setPoblacio(poblacio);
        }
        new PoblacioEvents(this);
    }

    public boolean esEdicio() {
        return poblacio != null;
    }
}