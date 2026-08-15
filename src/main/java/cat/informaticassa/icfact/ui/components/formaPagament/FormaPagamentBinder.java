package cat.informaticassa.icfact.ui.components.formaPagament;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;

public class FormaPagamentBinder {
    private final FormaPagamentPane formulari;

    public FormaPagamentBinder(FormaPagamentPane formulari) {
        this.formulari = formulari;
    }

    public void actualitzar(FormaPagament formaPagament) {
        formaPagament.setNom(formulari.getTxtNom().getText().trim());
        formaPagament.setDescripcio(formulari.getTxtDescripcio().getText().trim());
        formaPagament.setMostrarIban(formulari.getChkMostrarIban().isSelected());
        if (formaPagament.getId() != null) {
            formaPagament.setActiu(formulari.getChkActiu().isSelected());
        }
    }
}