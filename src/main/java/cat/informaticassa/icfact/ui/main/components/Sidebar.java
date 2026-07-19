package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.ui.main.pagines.PaginaInici;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.MenuPrincipal;
import com.sun.glass.ui.Menu;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class Sidebar extends VBox {

    private final SidebarButton botoInici;
    private final SidebarButton botoFactures;
    private final SidebarButton botoPressupostos;
    private final SidebarButton botoClients;
    private final SidebarButton botoArticles;
    private final SidebarButton botoInformes;
    private final SidebarButton botoConfiguracio;
    private final SidebarButton botoSobre;

    public Sidebar(Consumer<MenuPrincipal> onMenuClick) {

        setPrefWidth(240);
        setMinWidth(240);
        setMaxWidth(240);

        setSpacing(6);
        setPadding(new Insets(15));


        setBackground(new Background(new BackgroundFill(Tema.SIDEBAR, CornerRadii.EMPTY, Insets.EMPTY)));

        botoInici = new SidebarButton("Inici", "home.png");
        botoFactures = new SidebarButton("Factures", "factura.png");
        botoPressupostos = new SidebarButton("Pressupostos", "pressupost.png");
        botoClients = new SidebarButton("Clients", "clients.png");
        botoArticles = new SidebarButton("Articles", "articles.png");
        botoInformes = new SidebarButton("Informes", "informes.png");
        botoConfiguracio = new SidebarButton("Configuració", "configuracio.png");
        botoSobre = new SidebarButton("Sobre ICFact", "sobre.png");

        botoInici.seleccionar(true);

        Region espai = new Region();
        VBox.setVgrow(espai, Priority.ALWAYS);

        getChildren().addAll(
                botoInici,
                botoFactures,
                botoPressupostos,
                botoClients,
                botoArticles,
                botoInformes,
                espai,
                botoConfiguracio,
                botoSobre
        );

        botoInici.setOnAction(e -> {
            seleccionarBoto(botoInici);
            onMenuClick.accept(MenuPrincipal.INICI);
        });

        botoFactures.setOnAction(e -> {
            seleccionarBoto(botoFactures);
            onMenuClick.accept(MenuPrincipal.FACTURES);
        });

        botoPressupostos.setOnAction(e -> {
            seleccionarBoto(botoPressupostos);
            onMenuClick.accept(MenuPrincipal.PRESSUPOSTOS);
        });

        botoClients.setOnAction(e -> {
            seleccionarBoto(botoClients);
            onMenuClick.accept(MenuPrincipal.CLIENTS);
        });

        botoArticles.setOnAction(e -> {
            seleccionarBoto(botoArticles);
            onMenuClick.accept(MenuPrincipal.ARTICLES);
        });

        botoInformes.setOnAction(e -> {
            seleccionarBoto(botoInformes);
            onMenuClick.accept(MenuPrincipal.INFORMES);
        });

        botoConfiguracio.setOnAction(e -> {
            seleccionarBoto(botoConfiguracio);
            onMenuClick.accept(MenuPrincipal.CONFIGURACIO);
        });

        botoSobre.setOnAction(e -> {
            seleccionarBoto(botoSobre);
            onMenuClick.accept(MenuPrincipal.SOBRE);
        });
    }


    private void seleccionarBoto(SidebarButton seleccionat) {
        botoInici.seleccionar(false);
        botoFactures.seleccionar(false);
        botoPressupostos.seleccionar(false);
        botoClients.seleccionar(false);
        botoArticles.seleccionar(false);
        botoInformes.seleccionar(false);
        botoConfiguracio.seleccionar(false);
        botoSobre.seleccionar(false);
        seleccionat.seleccionar(true);
    }

}