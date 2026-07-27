package cat.informaticassa.icfact.ui.dialogs;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.ui.main.components.geografia.poblacio.PoblacioEvents;
import cat.informaticassa.icfact.ui.main.components.geografia.poblacio.PoblacioPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoblacioDialog extends DialogBase {

    private final PoblacioPane formulari = new PoblacioPane();
    private final PoblacioEvents events = new PoblacioEvents(this);

    private Poblacio poblacioCreada;

    public PoblacioDialog() {
        super("Nova població");
        getRoot().setCenter(formulari);
    }
}