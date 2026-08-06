package cat.informaticassa.icfact.ui.components.pressupost;

import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;

public class PressupostBinder {
    private final PressupostPane pane;

    public PressupostBinder(PressupostPane pane) {
        this.pane = pane;
    }

    public void carregar(Pressupost pressupost) {
        pane.getTxtNumero().setText(pressupost.getNumero());
        pane.getDpData().setValue(pressupost.getData());
        pane.getTxtClient().setSelectedItem(pressupost.getClient());
        pane.getCmbFormaPagament().setValue(pressupost.getFormaPagament());
        pane.getLblEstat().setText(pressupost.getEstat().name());
        pane.getTxtObservacions().setText(pressupost.getObservacions() == null ? "" : pressupost.getObservacions());
        pane.getTxtSubtotal().setText(pressupost.getSubtotal() == null ? "" : pressupost.getSubtotal().toPlainString());
        pane.getTxtIva().setText(pressupost.getIva() == null ? "" : pressupost.getIva().toPlainString());
        pane.getTxtTotal().setText(pressupost.getTotal() == null ? "" : pressupost.getTotal().toPlainString());
        pane.getTaulaLinies().mostrar(pressupost.getLinies());
        pane.getChkActiu().setSelected(pressupost.isActiu());
        pane.getChkActiu().setVisible(pressupost.getId() != null);
        pane.getChkActiu().setManaged(pressupost.getId() != null);
    }

    public void guardar(Pressupost pressupost) {
        pressupost.setNumero(pane.getTxtNumero().getText().trim());
        pressupost.setData(pane.getDpData().getValue());
        pressupost.setClient(pane.getTxtClient().getSelectedItem());
        pressupost.setFormaPagament(pane.getCmbFormaPagament().getValue());
        if (pressupost.getEstat() == null) {
            pressupost.setEstat(EstatPressupost.ESBORRANY);
        }
        pressupost.setActiu(
                pane.getChkActiu().isSelected()
        );
        pressupost.setObservacions(pane.getTxtObservacions().getText().trim());
        pressupost.setLinies(pane.getTaulaLinies().obtenirLinies());
    }
}