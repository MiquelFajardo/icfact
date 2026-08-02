package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.ui.components.producte.ProducteEvents;
import cat.informaticassa.icfact.ui.components.producte.ProductePane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducteDialog extends DialogBase {
    private final ProductePane formulari = new ProductePane();
    private Producte producte;

    public ProducteDialog() {
        this(null);
    }

    public ProducteDialog(Producte producte) {
        super(producte == null ? "Nou producte" : "Modificar producte", 750,400);
        this.producte = producte;
        getRoot().setCenter(formulari);
        formulari.registrarDirty(getDirtyTracker());
        if (producte == null) {
            formulari.mostrarCampActiu(false);
        } else {
            formulari.mostrar(producte);
            formulari.mostrarCampActiu(true);
        }
        new ProducteEvents(this);
    }

    public boolean esEdicio() {
        return producte != null;
    }
}