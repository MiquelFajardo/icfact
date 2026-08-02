package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.poblacio.CrearPoblacioService;
import cat.informaticassa.icfact.geografia.service.poblacio.ModificarPoblacioService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.components.geografia.poblacio.PoblacioPane;
import cat.informaticassa.icfact.ui.util.Alerta;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PoblacioDialog extends DialogBase {
    private final PoblacioPane formulari = new PoblacioPane();
    private final CrearPoblacioService crearPoblacioService = new CrearPoblacioService();
    private final ModificarPoblacioService modificarPoblacioService = new ModificarPoblacioService();
    private Poblacio poblacio;
    private Provincia provincia;

    public PoblacioDialog(Provincia provincia) {
        this(provincia, null);
    }

    public PoblacioDialog(Provincia provincia, Poblacio poblacio) {
        super(poblacio == null ? "Nova població" : "Modificar població", 500,250);
        this.provincia = provincia;
        this.poblacio = poblacio;
        getRoot().setCenter(formulari);
        formulari.setProvincia(provincia);
        if (poblacio != null) {
            formulari.setPoblacio(poblacio);
        }
        getBotoGuardar().setOnAction(e -> guardar());
        getBotoCancelar().setOnAction(e -> close());
    }

    private void guardar() {
        try {
            if (poblacio == null) {
                poblacio = new Poblacio();
                poblacio.setProvincia(provincia);
            }
            poblacio.setNom(
                    formulari.getTxtNom().getText().trim()
            );
            String cp = formulari.getTxtCodiPostal().getText().trim();
            if (cp.isBlank()) {
                poblacio.setCodiPostal(Set.of());
            } else {
                poblacio.setCodiPostal(Set.of(cp));
            }
            if (esEdicio()) {
                modificarPoblacioService.executar(poblacio);
            } else {
                crearPoblacioService.executar(poblacio);
            }
            close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            Alerta.error("No s'ha pogut guardar la població.");
        }
    }

    public boolean esEdicio() {
        return poblacio != null && poblacio.getId() != null;
    }
}