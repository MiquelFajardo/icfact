package cat.informaticassa.icfact.ui.components.producte;

import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.service.CrearProducteService;
import cat.informaticassa.icfact.producte.service.ModificarProducteService;
import cat.informaticassa.icfact.ui.components.dialogs.ProducteDialog;
import cat.informaticassa.icfact.ui.util.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducteEvents {
    private static final Logger logger = LoggerFactory.getLogger(ProducteEvents.class);
    private final ProducteDialog dialog;
    private final ProducteBinder binder;
    private final CrearProducteService crearProducteService = new CrearProducteService();
    private final ModificarProducteService modificarProducteService = new ModificarProducteService();

    public ProducteEvents(ProducteDialog dialog) {
        this.dialog = dialog;
        ProductePane formulari = dialog.getFormulari();
        this.binder = new ProducteBinder(formulari);
        dialog.getBotoGuardar().setOnAction(e -> guardar());
    }

    private void guardar() {
        try {
            Producte producte;
            if (dialog.esEdicio()) {
                producte = dialog.getProducte();
                binder.actualitzar(producte);
                modificarProducteService.executar(producte);
            } else {
                producte = new Producte();
                binder.actualitzar(producte);
                crearProducteService.executar(producte);
            }
            dialog.setProducte(producte);
            dialog.close();
        } catch (ValidacioException ex) {
            Alerta.error(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error inesperat desant el producte.", ex);
            Alerta.error("No s'ha pogut desar el producte.");
        }
    }
}