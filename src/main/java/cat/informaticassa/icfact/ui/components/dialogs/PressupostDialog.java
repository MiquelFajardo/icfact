package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.service.CrearPressupostService;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.pressupost.PressupostPane;
import cat.informaticassa.icfact.ui.components.pressupost.PressupostEvents;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class PressupostDialog extends DialogBase {
    private final PressupostPane formulari = new PressupostPane();
    private final CrearPressupostService crearPressupostService = new CrearPressupostService();
    private final BotoPrimari botoGenerarPdf =  new BotoPrimari("📄 Desa i genera PDF");
    private Pressupost pressupost;

    public PressupostDialog() {
        this(null);
    }

    public PressupostDialog(Pressupost pressupost) {
        super(
                pressupost == null ? "Nou pressupost" : "Modificar pressupost",
                1100,
                750
        );

        if (pressupost == null) {
            this.pressupost = new Pressupost();
            this.pressupost.setEstat(EstatPressupost.ESBORRANY);
            this.pressupost.setData(LocalDate.now());
            this.pressupost.setLinies(new ArrayList<>());
            this.pressupost.setSubtotal(BigDecimal.ZERO);
            this.pressupost.setIva(BigDecimal.ZERO);
            this.pressupost.setTotal(BigDecimal.ZERO);
            this.pressupost.setNumero(crearPressupostService.generarNumero());
        } else {
            this.pressupost = pressupost;
        }
        formulari.mostrar(this.pressupost);
        getRoot().setCenter(formulari);
        formulari.registrarDirty(getDirtyTracker());
        // Afegim el botó al costat del de desar
        getBotons().getChildren().add(2, botoGenerarPdf);

        new PressupostEvents(this);
    }

    public boolean esEdicio() {
        return pressupost.getId() != null;
    }
}