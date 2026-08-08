package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.service.CrearFacturaService;
import cat.informaticassa.icfact.infraestructura.model.TipusDocument;
import cat.informaticassa.icfact.infraestructura.service.GenerarNumeroDocumentService;
import cat.informaticassa.icfact.infraestructura.service.ObtenirSeguentNumeroDocumentService;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.factura.FacturaEvents;
import cat.informaticassa.icfact.ui.main.pagines.factura.FacturaPane;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

@Getter
@Setter
public class FacturaDialog extends DialogBase {
    private final FacturaPane formulari = new FacturaPane();
    private final CrearFacturaService crearFacturaService = new CrearFacturaService();
    private final ObtenirSeguentNumeroDocumentService numeroService = new ObtenirSeguentNumeroDocumentService();
    private final BotoPrimari botoGenerarPdf = new BotoPrimari("📄 Desa i genera PDF");
    private Factura factura;
    @Getter
    private boolean desadaCorrectament = false;

    public FacturaDialog() {
        this(null);
    }

    public FacturaDialog(Factura factura, boolean preparada) {
        this(factura);
        if (preparada) {
            getDirtyTracker().marcarModificat();
        }
    }

    public FacturaDialog(Factura factura) {
        super(factura == null ? "Nova factura" : "Modificar factura", 1100, 750);
        if (factura == null) {
            this.factura = new Factura();
            long numero = numeroService.obtenir(Year.now().getValue(), TipusDocument.FACTURA);
            this.factura.setNumero(GenerarNumeroDocumentService.generar("F",numero));
            this.factura.setEstat(EstatFactura.ESBORRANY);
            this.factura.setData(LocalDate.now());
            this.factura.setLinies(new ArrayList<>());
            this.factura.setSubtotal(BigDecimal.ZERO);
            this.factura.setIva(BigDecimal.ZERO);
            this.factura.setTotal(BigDecimal.ZERO);
        } else {
            this.factura = factura;
        }
        formulari.mostrar(this.factura);
        getRoot().setCenter(formulari);
        formulari.registrarDirty(getDirtyTracker());
        getBotons().getChildren().add(2, botoGenerarPdf);
        new FacturaEvents(this);
    }

    public boolean esEdicio() {
        return factura.getId() != null;
    }
}