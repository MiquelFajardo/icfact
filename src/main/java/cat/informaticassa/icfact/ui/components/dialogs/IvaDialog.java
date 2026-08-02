package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.ui.components.iva.IvaEvents;
import cat.informaticassa.icfact.ui.components.iva.IvaPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IvaDialog extends DialogBase {
    private final IvaPane formulari = new IvaPane();
    private Iva iva;

    public IvaDialog() {
        this(null);
    }

    public IvaDialog(Iva iva) {
        super(iva == null ? "Nou IVA" : "Modificar IVA", 500,250);
        this.iva = iva;
        getRoot().setCenter(formulari);
        formulari.registrarDirty(getDirtyTracker());
        if (iva == null) {
            formulari.mostrarCampActiu(false);
        } else {
            formulari.mostrar(iva);
            formulari.mostrarCampActiu(true);
        }
        new IvaEvents(this);
    }

    public boolean esEdicio() {
        return iva != null;
    }
}