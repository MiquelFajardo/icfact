package cat.informaticassa.icfact.ui.dialogs;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.main.components.geografia.provincia.ProvinciaEvents;
import cat.informaticassa.icfact.ui.main.components.geografia.provincia.ProvinciaPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinciaDialog extends DialogBase {
    private final ProvinciaPane formulari = new ProvinciaPane();
    private Provincia provincia;
    private Pais pais;

    public ProvinciaDialog(Pais pais) {
        this(pais, null);
    }

    public ProvinciaDialog(Pais pais, Provincia provincia) {
        super(provincia == null ? "Nova província" : "Modificar província");
        this.pais = pais;
        this.provincia = provincia;
        getRoot().setCenter(formulari);
        if (provincia != null) {
            formulari.setProvincia(provincia);
        }
        new ProvinciaEvents(this);
    }

    public boolean esEdicio() {
        return provincia != null;
    }
}