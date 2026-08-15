package cat.informaticassa.icfact.ui.components.producte;

import cat.informaticassa.icfact.producte.model.Producte;

public class ProducteBinder {
    private final ProductePane formulari;

    public ProducteBinder(ProductePane formulari) {
        this.formulari = formulari;
    }


    public void actualitzar(Producte producte) {
        producte.setCodi(formulari.getTxtCodi().getText() == null ? "" : formulari.getTxtCodi().getText().trim());
        producte.setNom(formulari.getTxtNom().getText() == null ? "" : formulari.getTxtNom().getText().trim());
        producte.setDescripcio(formulari.getTxtDescripcio().getText() == null ? "" : formulari.getTxtDescripcio().getText().trim());
        producte.setPreu(formulari.getTxtPreu().getValue());
        producte.setIva(formulari.getCmbIva().getValue());
        if (producte.getId() != null) {
            producte.setActiu(formulari.getChkActiu().isSelected());
        }
    }
}