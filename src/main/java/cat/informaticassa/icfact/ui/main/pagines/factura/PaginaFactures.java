package cat.informaticassa.icfact.ui.main.pagines.factura;

import cat.informaticassa.icfact.ui.main.pagines.factura.controller.FacturaController;
import cat.informaticassa.icfact.ui.main.pagines.factura.table.FacturaTable;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class PaginaFactures extends BorderPane {
    private final FacturaToolbar toolbar = new FacturaToolbar();
    private final FacturaTable taula = new FacturaTable();
    private final FacturaController controller;
    public PaginaFactures() {
        setTop(toolbar);
        setCenter(taula);
        controller = new FacturaController(this);
    }
}