package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.service.CrearTascaService;
import cat.informaticassa.icfact.tasca.service.ModificarTascaService;
import cat.informaticassa.icfact.tasca.service.ValidarTascaService;
import cat.informaticassa.icfact.ui.components.tasca.TascaPane;
import cat.informaticassa.icfact.ui.util.Alerta;
import lombok.Getter;

@Getter
public class TascaDialog extends DialogBase {
    private final TascaPane formulari = new TascaPane();
    private final CrearTascaService crearService = new CrearTascaService();
    private final ModificarTascaService modificarService = new ModificarTascaService();
    private final ValidarTascaService validarService = new ValidarTascaService();
    private final Tasca tasca;
    private final boolean edicio;
    private boolean desadaCorrectament = false;

    public TascaDialog() {
        this(new Tasca(), false);
    }

    public TascaDialog(Tasca tasca) {
        this(tasca, true);
    }

    private TascaDialog(Tasca tasca, boolean edicio) {
        super(edicio ? "Modificar tasca" : "Nova tasca",500, 250);
        if (tasca == null) {
            throw new IllegalArgumentException("La tasca no pot ser nul·la.");
        }
        this.tasca = tasca;
        this.edicio = edicio;
        formulari.mostrar(tasca);
        getRoot().setCenter(formulari);
        getBotoGuardar().setText("Desar");
        getBotoGuardar().setOnAction(e -> guardar());
    }

    private void guardar() {
        try {
            formulari.guardar(tasca);
            validarService.executar(tasca);
            if (edicio) {
                modificarService.executar(tasca);
            } else {
                crearService.executar(tasca);
            }
            desadaCorrectament = true;
            close();

        } catch (Exception ex) {
            Alerta.error(this, ex.getMessage());
        }
    }
}