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
    private Pais pais;

    public PaisDialog() {
        this(null);
    }

    public PaisDialog(Pais pais) {
        super(pais == null ? "Nou país" : "Modificar país");
        this.pais = pais;
        getRoot().setCenter(formulari);

        if (pais != null) {
            formulari.setPais(pais);
        }

        new PaisEvents(this);
    }

    public boolean esEdicio() {
        return pais != null;
    }
}