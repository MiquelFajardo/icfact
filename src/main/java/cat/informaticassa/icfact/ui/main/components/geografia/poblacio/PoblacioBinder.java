package cat.informaticassa.icfact.ui.main.components.geografia.poblacio;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;

import java.util.Collections;

public class PoblacioBinder {

    private final PoblacioPane vista;

    public PoblacioBinder(PoblacioPane vista) {
        this.vista = vista;
    }

    public void mostrar(Provincia provincia) {
        vista.getCmbProvincia().getItems().setAll(provincia);
        vista.getCmbProvincia().setValue(provincia);
    }

    public void mostrar(Poblacio poblacio) {
        mostrar(poblacio.getProvincia());
        vista.getTxtNom().setText(poblacio.getNom());
        vista.getTxtCodiPostal().setText(poblacio.getCodiPostal().toString());
    }

    public void actualitzar(Poblacio poblacio) {
        poblacio.setProvincia(vista.getCmbProvincia().getValue());
        poblacio.setNom(vista.getTxtNom().getText().trim());
        poblacio.setCodiPostal(Collections.singleton(vista.getTxtCodiPostal().getText().trim()));
    }
}