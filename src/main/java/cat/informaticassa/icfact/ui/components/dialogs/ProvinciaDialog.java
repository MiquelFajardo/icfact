package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.geografia.service.provincia.CrearProvinciaService;
import cat.informaticassa.icfact.geografia.service.provincia.ModificarProvinciaService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.components.geografia.provincia.ProvinciaPane;
import cat.informaticassa.icfact.ui.util.Alerta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinciaDialog extends DialogBase {
    private final ProvinciaPane formulari = new ProvinciaPane();
    private final CrearProvinciaService crearProvinciaService = new CrearProvinciaService();
    private final ModificarProvinciaService modificarProvinciaService = new ModificarProvinciaService();
    private Provincia provincia;
    private Pais pais;

    public ProvinciaDialog(Pais pais) {
        this(pais, null);
    }

    public ProvinciaDialog(Pais pais, Provincia provincia) {
        super(provincia == null ? "Nova província" : "Modificar província", false);
        this.pais = pais;
        this.provincia = provincia;
        getRoot().setCenter(formulari);
        formulari.setPais(pais);
        if (provincia != null) {
            formulari.setProvincia(provincia);
        }
        getBotoGuardar().setOnAction(e -> guardar());
        getBotoCancelar().setOnAction(e -> close());
    }

    private void guardar() {
        try {
            if (provincia == null) {
                provincia = new Provincia();
                provincia.setPais(pais);
            }
            provincia.setNom(
                    formulari.getTxtNom().getText().trim()
            );
            provincia.setCodi(
                    formulari.getTxtCodi().getText().trim()
            );
            if (esEdicio()) {
                modificarProvinciaService.executar(provincia);
            } else {
                crearProvinciaService.executar(provincia);
            }
            close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            Alerta.error("No s'ha pogut guardar la província.");
        }
    }

    public boolean esEdicio() {
        return provincia != null && provincia.getId() != null;
    }
}