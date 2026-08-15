package cat.informaticassa.icfact.ui.components.formaPagament;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.service.CrearFormaPagamentService;
import cat.informaticassa.icfact.formaPagament.service.ModificarFormaPagamentService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.components.dialogs.FormaPagamentDialog;
import cat.informaticassa.icfact.ui.util.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormaPagamentEvents {
    private static final Logger logger = LoggerFactory.getLogger(FormaPagamentEvents.class);
    private final FormaPagamentDialog dialog;
    private final FormaPagamentBinder binder;
    private final CrearFormaPagamentService crearFormaPagamentService = new CrearFormaPagamentService();
    private final ModificarFormaPagamentService modificarFormaPagamentService = new ModificarFormaPagamentService();

    public FormaPagamentEvents(FormaPagamentDialog dialog) {
        this.dialog = dialog;
        FormaPagamentPane formulari = dialog.getFormulari();
        this.binder = new FormaPagamentBinder(formulari);
        dialog.getBotoGuardar().setOnAction(e -> guardar());
    }

    private void guardar() {
        try {
            FormaPagament formaPagament;
            if (dialog.esEdicio()) {
                formaPagament = dialog.getFormaPagament();
                binder.actualitzar(formaPagament);
                modificarFormaPagamentService.executar(formaPagament);
            } else {
                formaPagament = new FormaPagament();
                binder.actualitzar(formaPagament);
                crearFormaPagamentService.executar(formaPagament);
            }
            dialog.setFormaPagament(formaPagament);
            dialog.close();
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperat desant la forma de pagament.", e);
            Alerta.error("No s'ha pogut desar la forma de pagament.");
        }
    }
}