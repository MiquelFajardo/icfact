package cat.informaticassa.icfact.ui.main.pagines.producte;

import cat.informaticassa.icfact.ui.main.pagines.producte.controller.ProducteController;
import cat.informaticassa.icfact.ui.main.pagines.producte.table.ProducteTable;
import cat.informaticassa.icfact.ui.main.pagines.producte.toolbar.ProducteToolbar;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class PaginaProductes extends BorderPane {
    private final ProducteToolbar toolbar = new ProducteToolbar();
    private final ProducteTable taula = new ProducteTable();
    private final ProducteController controller;

    public PaginaProductes() {
        setPadding(new Insets(20));
        setTop(toolbar);
        setCenter(taula);
        controller = new ProducteController(this);
    }
}