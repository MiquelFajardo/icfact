package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.service.BuscarFormesPagamentService;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.ui.components.pagament.PagamentDialogEvents;
import cat.informaticassa.icfact.ui.components.pagament.PagamentPane;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class PagamentDialog extends DialogBase {
    private final PagamentPane formulari = new PagamentPane();
    private final PagamentRepository pagamentRepository = new PagamentRepository();
    private final Pagament pagament;
    private final BigDecimal total;
    private final BigDecimal pagat;
    private final Pressupost pressupost;
    private final Factura factura;

    public PagamentDialog(Pressupost pressupost) {
        super("Afegir pagament", 600, 600);
        if (pressupost == null) {
            throw new IllegalArgumentException("El pressupost no pot ser nul.");
        }
        if (pressupost.getEstat() == EstatPressupost.FACTURAT) {
            throw new IllegalArgumentException("Aquest pressupost ja està facturat. " + "Els nous pagaments s'han de registrar des de la factura.");
        }
        this.pressupost = pressupost;
        this.factura = null;
        this.total = pressupost.getTotal() == null ? BigDecimal.ZERO : pressupost.getTotal();
        this.pagat = pagamentRepository.calcularImportPagat(pressupost);
        this.pagament = new Pagament();
        this.pagament.setPressupost(pressupost);
        inicialitzar();
    }

    public PagamentDialog(Factura factura) {
        super("Afegir pagament", 600, 600);
        if (factura == null) {
            throw new IllegalArgumentException("La factura no pot ser nul·la.");
        }
        this.pressupost = null;
        this.factura = factura;
        this.total = factura.getTotal() == null ? BigDecimal.ZERO : factura.getTotal();
        this.pagat = pagamentRepository.calcularImportPagat(factura);
        this.pagament = new Pagament();
        this.pagament.setFactura(factura);
        if (factura.getPressupost() != null) {
            this.pagament.setPressupost(factura.getPressupost());
        }
        inicialitzar();
    }

    private void inicialitzar() {
        formulari.mostrarResum(total, pagat);
        getRoot().setCenter(formulari);
        formulari.registrarDirty(getDirtyTracker());
        getBotoGuardar().setText("💶 Afegir pagament");
        new PagamentDialogEvents(this);
    }

    public BigDecimal getResta() {
        return total.subtract(pagat);
    }

    public boolean esPressupost() {
        return pressupost != null;
    }

    public boolean esFactura() {
        return factura != null;
    }
}