package cat.informaticassa.icfact.ui.components.producte;

import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.service.BuscarIvaService;
import cat.informaticassa.icfact.ui.components.dialogs.IvaDialog;
import javafx.util.StringConverter;

public class ProductePaneController {
    private final ProductePane pane;

    private final BuscarIvaService buscarIvaService = new BuscarIvaService();

    public ProductePaneController(ProductePane pane) {
        this.pane = pane;
        inicialitzar();
    }

    private void inicialitzar() {
        pane.getCmbIva().setConverter(new StringConverter<>() {
            @Override
            public String toString(Iva iva) {
                if (iva == null) {
                    return "";
                }
                return iva.getNom() + " (" +
                        iva.getPercentatge().stripTrailingZeros().toPlainString() +
                        " %)";
            }
            @Override
            public Iva fromString(String string) {
                return null;
            }
        });
        pane.getBotoNouIva().setOnAction(e -> nouIva());
        carregarIves();
    }

    private void carregarIves() {
        pane.getCmbIva().getItems().setAll(
                buscarIvaService.buscarActius()
        );
    }

    private void nouIva() {
        IvaDialog dialog = new IvaDialog();
        dialog.initOwner(pane.getScene().getWindow());
        dialog.showAndWait();
        Iva ivaCreat = dialog.getIva();
        if (ivaCreat == null) {
            return;
        }
        carregarIves();
        pane.getCmbIva().getItems().stream()
                .filter(iva -> iva.getId().equals(ivaCreat.getId()))
                .findFirst()
                .ifPresent(pane.getCmbIva()::setValue);
    }
}