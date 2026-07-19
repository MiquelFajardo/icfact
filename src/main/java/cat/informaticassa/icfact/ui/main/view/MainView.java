package cat.informaticassa.icfact.ui.main.view;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.main.components.Sidebar;
import cat.informaticassa.icfact.ui.main.components.TopBar;
import cat.informaticassa.icfact.ui.main.components.WorkArea;
import cat.informaticassa.icfact.ui.main.controller.MainController;
import cat.informaticassa.icfact.ui.main.pagines.PaginaInici;
import cat.informaticassa.icfact.ui.util.MenuPrincipal;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    private final WorkArea workArea = new WorkArea();

    public MainView(Empresa empresa) {
        TopBar topBar = new TopBar(empresa);
        Sidebar sidebar = new Sidebar(this::canviarPagina);
        setTop(topBar);
        setLeft(sidebar);
        setCenter(workArea);
        canviarPagina(MenuPrincipal.INICI);
        new MainController(this);
    }

    private void canviarPagina(MenuPrincipal pagina) {

        switch (pagina) {
            case INICI -> workArea.mostrar(new PaginaInici());
            default -> workArea.mostrar(new PaginaInici());
        }
    }
}