package cat.informaticassa.icfact.ui.components.factura;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.ui.main.pagines.factura.FacturaPane;

public class FacturaBinder {
    private final FacturaPane pane;

    public FacturaBinder(FacturaPane pane) {
        this.pane = pane;
    }

    public void carregar(Factura factura) {
        pane.getTxtNumero().setText(factura.getNumero());
        pane.getDpData().setValue(factura.getData());
        pane.getTxtClient().setSelectedItem(factura.getClient() );
        pane.getCmbFormaPagament().setValue(factura.getFormaPagament());
        pane.getLblEstat().setText(factura.getEstat() == null ? EstatFactura.ESBORRANY.name() : factura.getEstat().name());
        pane.getTxtObservacions().setText(factura.getObservacions() == null ? "" : factura.getObservacions());
        pane.getTxtSubtotal().setText(factura.getSubtotal() == null ? "" : factura.getSubtotal().toPlainString());
        pane.getTxtIva().setText( factura.getIva() == null ? "" : factura.getIva().toPlainString());
        pane.getTxtTotal().setText(factura.getTotal() == null ? "" : factura.getTotal().toPlainString());
        pane.getTaulaLinies().mostrar(factura.getLinies());
        pane.getChkActiu().setSelected(factura.isActiu());
        pane.getChkActiu().setVisible(factura.getId() != null);
        pane.getChkActiu().setManaged(factura.getId() != null);
    }

    public void guardar(Factura factura) {
        factura.setNumero(pane.getTxtNumero().getText().trim());
        factura.setData(pane.getDpData().getValue());
        factura.setClient(pane.getTxtClient().getSelectedItem());
        factura.setFormaPagament(pane.getCmbFormaPagament().getValue());
        if (factura.getEstat() == null) {
            factura.setEstat(
                    EstatFactura.ESBORRANY
            );
        }
        factura.setActiu( pane.getChkActiu().isSelected());
        factura.setObservacions(pane.getTxtObservacions().getText().trim());
        factura.setLinies(pane.getTaulaLinies().obtenirLinies());
    }
}