package cat.informaticassa.icfact.ui.main.view;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.main.components.Sidebar;
import cat.informaticassa.icfact.ui.main.components.TopBar;
import cat.informaticassa.icfact.ui.main.components.WorkArea;
import cat.informaticassa.icfact.ui.main.controller.MainController;
import cat.informaticassa.icfact.ui.main.pagines.PaginaInici;
import cat.informaticassa.icfact.ui.main.pagines.client.PaginaClients;
import cat.informaticassa.icfact.ui.main.pagines.empresa.DadesEmpresa;
import cat.informaticassa.icfact.ui.main.pagines.geografia.PaginaGeografia;
import cat.informaticassa.icfact.ui.main.pagines.iva.PaginaIVA;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.MenuPrincipal;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public class MainView extends BorderPane {
    private final WorkArea workArea = new WorkArea();
    private final TopBar topBar;

    public MainView(Empresa empresa) {
        if (empresa.getColor() != null && !empresa.getColor().isBlank()) {
            Tema.setColorPrincipal(Color.web(empresa.getColor()));
        }
        topBar = new TopBar(empresa.getNom());
        MainController controller = new MainController(this);
        topBar.setOnSortir(controller::sortirAplicacio);
        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                return;
            }
            newScene.windowProperty().addListener((o, oldWindow, newWindow) -> {
                if (newWindow == null) {
                    return;
                }
                newWindow.setOnCloseRequest(e -> {
                    e.consume();
                    controller.sortirAplicacio();
                });
            });
        });

        Sidebar sidebar = new Sidebar(controller::canviarPagina);
        setTop(topBar);
        setLeft(sidebar);
        setCenter(workArea);
        mostrarPagina(MenuPrincipal.INICI);
    }

    public void mostrarPagina(MenuPrincipal pagina) {
        switch (pagina) {
            case INICI -> workArea.mostrar(new PaginaInici());
            case DADES_EMPRESA -> workArea.mostrar(new DadesEmpresa());
            case CLIENTS -> workArea.mostrar(new PaginaClients());
            case IVA -> workArea.mostrar(new PaginaIVA());
            case GEOGRAFIA -> workArea.mostrar(new PaginaGeografia());
            default -> workArea.mostrar(new PaginaInici());
        }
    }
}