package cat.informaticassa.icfact.ui.main.pagines.iva;

import cat.informaticassa.icfact.ui.main.pagines.iva.controller.IvaController;
import cat.informaticassa.icfact.ui.main.pagines.iva.table.IvaTable;
import cat.informaticassa.icfact.ui.main.pagines.iva.toolbar.IvaToolbar;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class PaginaIVA extends BorderPane {
    private final IvaToolbar toolbar = new IvaToolbar();
    private final IvaTable taula = new IvaTable();

    public PaginaIVA() {
        setPadding(new Insets(20));
        setTop(toolbar);
        setCenter(taula);
        new IvaController(this);
    }
}