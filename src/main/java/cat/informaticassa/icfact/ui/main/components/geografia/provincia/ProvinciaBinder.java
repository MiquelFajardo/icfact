package cat.informaticassa.icfact.ui.main.components.geografia.provincia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;

public class ProvinciaBinder {

    private final ProvinciaPane vista;

    public ProvinciaBinder(ProvinciaPane vista) {
        this.vista = vista;
    }

    public void mostrar(Pais pais) {
        vista.getCmbPais().getItems().setAll(pais);
        vista.getCmbPais().setValue(pais);
    }

    public void mostrar(Provincia provincia) {
        mostrar(provincia.getPais());
        vista.getTxtNom().setText(provincia.getNom());
        vista.getTxtCodi().setText(provincia.getCodi());
    }

    public void actualitzar(Provincia provincia) {
        provincia.setPais(vista.getCmbPais().getValue());
        provincia.setNom(vista.getTxtNom().getText().trim());
        provincia.setCodi(vista.getTxtCodi().getText().trim());
    }
}