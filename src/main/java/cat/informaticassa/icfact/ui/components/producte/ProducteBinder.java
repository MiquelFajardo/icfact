package cat.informaticassa.icfact.ui.components.producte;

import cat.informaticassa.icfact.producte.model.Producte;

public class ProducteBinder {
    private final ProductePane formulari;

    public ProducteBinder(ProductePane formulari) {
        this.formulari = formulari;
    }

    public void carregar(Producte producte) {
        formulari.getTxtCodi().setText(producte.getCodi());
        formulari.getTxtNom().setText(producte.getNom());
        formulari.getTxtDescripcio().setText(
                producte.getDescripcio() == null
                        ? ""
                        : producte.getDescripcio()
        );

        formulari.getTxtPreu().setValue(producte.getPreu());
        formulari.getCmbIva().setValue(producte.getIva());
        formulari.getChkActiu().setSelected(producte.isActiu());
    }

    public void actualitzar(Producte producte) {
        producte.setCodi(formulari.getTxtCodi().getText().trim());
        producte.setNom(formulari.getTxtNom().getText().trim());
        producte.setDescripcio(formulari.getTxtDescripcio().getText().trim());
        producte.setPreu(formulari.getTxtPreu().getValue());
        producte.setIva(formulari.getCmbIva().getValue());
        // Només es modifica l'estat si és una edició
        if (producte.getId() != null) {
            producte.setActiu(
                    formulari.getChkActiu().isSelected()
            );
        }
    }
}