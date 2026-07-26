package cat.informaticassa.icfact.ui.dialogs;

import cat.informaticassa.icfact.ui.main.components.geografia.PaisEvents;
import cat.informaticassa.icfact.ui.main.components.geografia.PaisPane;
import lombok.Getter;

@Getter
public class PaisDialog extends DialogBase {

    private static PaisPane crearFormulari() {
        return new PaisPane();
    }

    private final PaisPane formulari;

    public PaisDialog() {
        this(crearFormulari());
    }

    private PaisDialog(PaisPane formulari) {
        super("Nou país", formulari);
        this.formulari = formulari;
        new PaisEvents(this);
    }
}