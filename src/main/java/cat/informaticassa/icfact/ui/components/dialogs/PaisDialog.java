package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.service.pais.CrearPaisService;
import cat.informaticassa.icfact.geografia.service.pais.ModificarPaisService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.components.geografia.pais.PaisPane;
import cat.informaticassa.icfact.ui.util.Alerta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaisDialog extends DialogBase {
    private final PaisPane formulari = new PaisPane();
    private final CrearPaisService crearPaisService = new CrearPaisService();
    private final ModificarPaisService modificarPaisService = new ModificarPaisService();
    private Pais pais;

    public PaisDialog() {
        this(null);
    }

    public PaisDialog(Pais pais) {
        super(pais == null ? "Nou país" : "Modificar país", 500,250);
        this.pais = pais;
        getRoot().setCenter(formulari);
        if (pais != null) {
            formulari.setPais(pais);
        }
        getBotoGuardar().setOnAction(e -> guardar());
        getBotoCancelar().setOnAction(e -> close());
    }

    private void guardar() {
        try {
            if (pais == null) {
                pais = new Pais();
            }
            pais.setNom(formulari.getTxtNom().getText().trim());
            pais.setCodiIso(formulari.getTxtCodiIso().getText().trim());
            if (esEdicio()) {
                modificarPaisService.executar(pais);
            } else {
                crearPaisService.executar(pais);
            }
            close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            Alerta.error("No s'ha pogut guardar el país.");
        }
    }

    public boolean esEdicio() {
        return pais != null && pais.getId() != null;
    }
}