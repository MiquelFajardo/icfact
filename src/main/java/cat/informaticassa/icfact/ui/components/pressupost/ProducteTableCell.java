package cat.informaticassa.icfact.ui.components.pressupost;

import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.service.RecalcularLiniaPressupostService;
import cat.informaticassa.icfact.ui.components.dialogs.ProducteDialog;
import cat.informaticassa.icfact.ui.components.dialogs.ProducteNoExisteixDialog;
import javafx.scene.control.TableCell;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.service.BuscarProducteService;
import cat.informaticassa.icfact.ui.util.searchable.SearchField;

import java.math.BigDecimal;

public class ProducteTableCell extends TableCell<LiniaPressupost, String> {
    private final SearchField<Producte> editor = new SearchField<>();
    private final BuscarProducteService buscarProducteService = new BuscarProducteService();
    private final RecalcularLiniaPressupostService recalcularService = new RecalcularLiniaPressupostService();

    public ProducteTableCell() {
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
        LiniaPressupost linia = getTableRow().getItem();
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
                recalcularService.executar(linia);
                commitEdit(editor.getText());
                getTableView().refresh();
                if (getTableView() instanceof PressupostLiniesTable taula) {
                    taula.comprovarUltimaLinia();
                    taula.refresh();
                    if (taula.getPressupostPane() != null) {
                        taula.getPressupostPane().actualitzarTotals();
                    }
                }
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
        recalcularService.executar(linia);
        commitEdit(producte.getNom());
        getTableView().refresh();
        if (getTableView() instanceof PressupostLiniesTable taula) {
            taula.comprovarUltimaLinia();
            taula.refresh();
            if (taula.getPressupostPane() != null) {
                taula.getPressupostPane().actualitzarTotals();
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        LiniaPressupost linia = getTableRow().getItem();
        if (linia == null) {
            return;
        }
        editor.setText(getItem() == null ? "" : getItem());
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
        super.updateItem(item, empty);
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