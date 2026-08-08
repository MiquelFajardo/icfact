package cat.informaticassa.icfact.ui.main.view;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.main.components.Sidebar;
import cat.informaticassa.icfact.ui.main.components.TopBar;
import cat.informaticassa.icfact.ui.main.components.WorkArea;
import cat.informaticassa.icfact.ui.main.controller.MainController;
import cat.informaticassa.icfact.ui.main.pagines.PaginaInici;
import cat.informaticassa.icfact.ui.main.pagines.client.PaginaClients;
import cat.informaticassa.icfact.ui.main.pagines.empresa.DadesEmpresa;
import cat.informaticassa.icfact.ui.main.pagines.factura.PaginaFactures;
import cat.informaticassa.icfact.ui.main.pagines.formaPagament.PaginaFormesPagament;
import cat.informaticassa.icfact.ui.main.pagines.geografia.PaginaGeografia;
import cat.informaticassa.icfact.ui.main.pagines.iva.PaginaIVA;
import cat.informaticassa.icfact.ui.main.pagines.pressupost.PaginaPressupostos;
import cat.informaticassa.icfact.ui.main.pagines.producte.PaginaProductes;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.MenuPrincipal;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public class MainView extends BorderPane {
    private final WorkArea workArea = new WorkArea();
    private final TopBar topBar;
    private final PaginaInici paginaInici = new PaginaInici();
    private final DadesEmpresa paginaEmpresa = new DadesEmpresa();
    private final PaginaClients paginaClients = new PaginaClients();
    private final PaginaProductes paginaProductes = new PaginaProductes();
    private final PaginaIVA paginaIVA = new PaginaIVA();
    private final PaginaFormesPagament paginaFormesPagament = new PaginaFormesPagament();
    private final PaginaPressupostos paginaPressupostos = new PaginaPressupostos();
    private final PaginaFactures paginaFactures = new PaginaFactures();
    private final PaginaGeografia paginaGeografia = new PaginaGeografia();
    private final MainController controller;

    public MainView(Empresa empresa) {
        if (empresa.getColor() != null && !empresa.getColor().isBlank()) {
            Tema.setColorPrincipal(Color.web(empresa.getColor()));
        }

        topBar = new TopBar(empresa.getNom());
        controller = new MainController(this);
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
            case INICI -> workArea.mostrar(paginaInici);
            case DADES_EMPRESA -> workArea.mostrar(paginaEmpresa);
            case CLIENTS -> workArea.mostrar(paginaClients);
            case PRODUCTES -> workArea.mostrar(paginaProductes);
            case IVA -> workArea.mostrar(paginaIVA);
            case FORMA_DE_PAGAMENT -> workArea.mostrar(paginaFormesPagament);
            case PRESSUPOSTOS -> {
                paginaPressupostos.getController().treureFiltreClient();
                workArea.mostrar(paginaPressupostos);
            }
            case FACTURES -> {
                paginaFactures.getController().treureFiltreClient();
                workArea.mostrar(paginaFactures);
            }
            case GEOGRAFIA -> workArea.mostrar(paginaGeografia);
            default -> workArea.mostrar(paginaInici);
        }
    }

    public void mostrarPressupostos(Client client) {
        mostrarPagina(MenuPrincipal.PRESSUPOSTOS);
        paginaPressupostos.getController().mostrarPressupostosClient(client);
    }

    public void mostrarFactures(Client client) {
        mostrarPagina(MenuPrincipal.FACTURES);
        paginaFactures.getController().mostrarFacturesClient(client);
    }
}