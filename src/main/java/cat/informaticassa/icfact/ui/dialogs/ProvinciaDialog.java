package cat.informaticassa.icfact.ui.dialogs;

import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.main.components.geografia.provincia.ProvinciaEvents;
import cat.informaticassa.icfact.ui.main.components.geografia.provincia.ProvinciaPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinciaDialog extends DialogBase {
    private final ProvinciaPane formulari = new ProvinciaPane();
    private final ProvinciaEvents events = new ProvinciaEvents(this);
    private Provincia provinciaCreada;

    public ProvinciaDialog() {
        super("Nova província");
        getRoot().setCenter(formulari);
    }
}