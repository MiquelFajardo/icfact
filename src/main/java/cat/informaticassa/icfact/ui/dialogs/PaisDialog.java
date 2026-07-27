package cat.informaticassa.icfact.ui.dialogs;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.ui.main.components.geografia.pais.PaisEvents;
import cat.informaticassa.icfact.ui.main.components.geografia.pais.PaisPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaisDialog extends DialogBase {
    private final PaisPane formulari = new PaisPane();
    private Pais paisCreat;

    public PaisDialog() {
        super("Nou país");
        getRoot().setCenter(formulari);
        new PaisEvents(this);
    }
}