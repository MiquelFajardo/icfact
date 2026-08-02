package cat.informaticassa.icfact.ui.main.pagines.formaPagament;

import cat.informaticassa.icfact.ui.main.pagines.formaPagament.controller.FormaPagamentController;
import cat.informaticassa.icfact.ui.main.pagines.formaPagament.table.FormaPagamentTable;
import cat.informaticassa.icfact.ui.main.pagines.formaPagament.toolbar.FormaPagamentToolbar;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class PaginaFormesPagament extends BorderPane {
    private final FormaPagamentToolbar toolbar = new FormaPagamentToolbar();
    private final FormaPagamentTable taula = new FormaPagamentTable();

    public PaginaFormesPagament() {

        setPadding(new Insets(20));

        setTop(toolbar);
        setCenter(taula);

        new FormaPagamentController(this);
    }
}