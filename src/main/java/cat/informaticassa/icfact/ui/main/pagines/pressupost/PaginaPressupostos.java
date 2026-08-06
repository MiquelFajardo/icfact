package cat.informaticassa.icfact.ui.main.pagines.pressupost;

import cat.informaticassa.icfact.ui.main.pagines.pressupost.controller.PressupostController;
import cat.informaticassa.icfact.ui.main.pagines.pressupost.table.PressupostTable;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class PaginaPressupostos extends BorderPane {
    private final PressupostToolbar toolbar = new PressupostToolbar();
    private final PressupostTable taula = new PressupostTable();
    private final PressupostController controller;

    public PaginaPressupostos() {
        setTop(toolbar);
        setCenter(taula);
        controller = new PressupostController(this);
    }
}