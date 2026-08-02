package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.ui.components.formaPagament.FormaPagamentEvents;
import cat.informaticassa.icfact.ui.components.formaPagament.FormaPagamentPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentDialog extends DialogBase {
    private final FormaPagamentPane formulari = new FormaPagamentPane();
    private FormaPagament formaPagament;

    public FormaPagamentDialog() {
        this(null);
    }

    public FormaPagamentDialog(FormaPagament formaPagament) {
        super(formaPagament == null ? "Nova forma de pagament" : "Modificar forma de pagament",500,350);

        this.formaPagament = formaPagament;
        getRoot().setCenter(formulari);
        formulari.registrarDirty(getDirtyTracker());
        if (formaPagament == null) {
            formulari.mostrarCampActiu(false);
        } else {
            formulari.mostrar(formaPagament);
            formulari.mostrarCampActiu(true);
        }
        new FormaPagamentEvents(this);
    }

    public boolean esEdicio() {
        return formaPagament != null;
    }
}