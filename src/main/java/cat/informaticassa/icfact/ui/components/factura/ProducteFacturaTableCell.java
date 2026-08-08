package cat.informaticassa.icfact.ui.components.factura;

import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.service.RecalcularFacturaService;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.service.BuscarProducteService;
import cat.informaticassa.icfact.ui.components.dialogs.ProducteDialog;
import cat.informaticassa.icfact.ui.components.dialogs.ProducteNoExisteixDialog;
import cat.informaticassa.icfact.ui.util.searchable.SearchField;
import javafx.scene.control.TableCell;
import java.math.BigDecimal;
import java.util.List;

public class ProducteFacturaTableCell extends TableCell<LiniaFactura, String> {
    private final SearchField<Producte> editor = new SearchField<>();
    private final BuscarProducteService buscarProducteService = new BuscarProducteService();
    private final RecalcularFacturaService recalcularService = new RecalcularFacturaService();

    public ProducteFacturaTableCell() {
        editor.setDisplayFunction(Producte::getNom);
        editor.setSearchFunction(producte -> producte.getCodi() + " " + producte.getNom());
        editor.setItems(buscarProducteService.buscarActius());
        editor.setOnAction(e -> gestionarEntrada());
    }

    private void gestionarEntrada() {
        Producte producte = editor.getSelectedItem();
        if (producte == null) {
            producte = buscarProducteService.buscarPerCodiONom(editor.getText());
        }
        LiniaFactura linia = getTableRow().getItem();
        if (linia == null) {
            return;
        }
        if (producte == null) {
            ProducteNoExisteixDialog dialog = new ProducteNoExisteixDialog(editor.getText());
            dialog.showAndWait();
            if (dialog.isCrearProducte()) {
                ProducteDialog producteDialog = new ProducteDialog(editor.getText());
                producteDialog.showAndWait();
                producte = producteDialog.getProducte();
                if (producte == null) {
                    return;
                }
            } else {
                linia.setProducte(null);
                linia.setDescripcio(editor.getText());
                linia.setQuantitat(BigDecimal.ONE);
                linia.setPreu(BigDecimal.ZERO);
                linia.setDte(BigDecimal.ZERO);
                getTableView().refresh();
                if (getTableView()
                        instanceof FacturaLiniesTable taula) {
                    taula.comprovarUltimaLinia();
                    taula.refresh();
                    if (taula.getFacturaPane() != null) {
                        taula.getFacturaPane().actualitzarTotals();
                    }
                }
                commitEdit(editor.getText());
                return;
            }
        }
        linia.setProducte(producte);
        linia.setDescripcio(producte.getNom());
        linia.setPreu(producte.getPreu());
        linia.setIva(producte.getIva());
        if (linia.getQuantitat() == null || linia.getQuantitat().signum() == 0) {
            linia.setQuantitat(BigDecimal.ONE);
        }

        if (linia.getDte() == null) {
            linia.setDte(BigDecimal.ZERO);
        }

        if (linia.getIva() != null) {
            FacturaLiniesTable taula =(FacturaLiniesTable) getTableView();
            recalcularService.executar(new cat.informaticassa.icfact.factura.model.Factura() {{
                        setLinies(List.of(linia));
                    }}
            );
            taula.comprovarUltimaLinia();
            taula.refresh();
            if (taula.getFacturaPane() != null) {
                taula.getFacturaPane().actualitzarTotals();
            }
        }
        commitEdit(producte.getNom());
        getTableView().refresh();
    }

    @Override
    public void startEdit() {
        super.startEdit();
        LiniaFactura linia = getTableRow().getItem();
        if (linia == null) {
            return;
        }

        editor.setText(getItem() == null  ? "" : getItem());
        setText(null);
        setGraphic(editor);
        editor.requestFocus();
        editor.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
        setText(getItem());
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item,empty);
        if (empty) {
            setGraphic(null);
            setText(null);
            return;
        }
        if (isEditing()) {
            editor.setText(item == null ? "" : item);
            setGraphic(editor);
            setText(null);
        } else {
            setGraphic(null);
            setText(item);
        }
    }
}