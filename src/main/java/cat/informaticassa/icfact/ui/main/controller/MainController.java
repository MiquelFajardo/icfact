package cat.informaticassa.icfact.ui.main.controller;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.ui.main.view.MainView;
import cat.informaticassa.icfact.ui.util.Alerta;
import cat.informaticassa.icfact.ui.util.MenuPrincipal;
import cat.informaticassa.icfact.ui.util.dirty.DirtyManager;
import cat.informaticassa.icfact.ui.util.dirty.DirtyPage;
import cat.informaticassa.icfact.ui.util.dirty.DirtyProvider;
import javafx.scene.Node;

public class MainController {
    private final MainView view;

    public MainController(MainView view) {
        this.view = view;
    }

    public void canviarPagina(MenuPrincipal pagina) {
        Node actual = view.getWorkArea().getPaginaActual();
        if (actual instanceof DirtyProvider provider) {
            DirtyPage dirtyPage = provider.getDirtyPage();
            boolean continuar = DirtyManager.sortir(
                    dirtyPage,
                    view.getScene().getWindow()
            );
            if (!continuar) {
                return;
            }
        }
        view.mostrarPagina(pagina);
    }

    public void obrirPressupostos(Client client) {
        canviarPagina(MenuPrincipal.PRESSUPOSTOS);
        view.getPaginaPressupostos()
                .getController()
                .mostrarPressupostosClient(client);
    }

    public void sortirAplicacio() {
        Node actual = view.getWorkArea().getPaginaActual();
        if (actual instanceof DirtyProvider provider) {
            DirtyPage dirtyPage = provider.getDirtyPage();
            boolean continuar = DirtyManager.sortir(
                    dirtyPage,
                    view.getScene().getWindow()
            );
            if (!continuar) {
                return;
            }
        }
        boolean sortir = Alerta.confirmar(view.getScene().getWindow(),"Sortir","Vols sortir d'ICFact?");
        if (!sortir) {
            return;
        }
        javafx.application.Platform.exit();
    }
}