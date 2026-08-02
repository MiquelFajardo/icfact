package cat.informaticassa.icfact.ui.components.formaPagament;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;

public class FormaPagamentBinder {
    private final FormaPagamentPane formulari;

    public FormaPagamentBinder(FormaPagamentPane formulari) {
        this.formulari = formulari;
    }

    public void carregar(FormaPagament formaPagament) {
        formulari.getTxtNom().setText(formaPagament.getNom());
        formulari.getTxtDescripcio().setText(
                formaPagament.getDescripcio() == null
                        ? ""
                        : formaPagament.getDescripcio()
        );
        formulari.getChkMostrarIban().setSelected(Boolean.TRUE.equals(formaPagament.getMostrarIban()) );
        formulari.getChkActiu().setSelected(formaPagament.isActiu());
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