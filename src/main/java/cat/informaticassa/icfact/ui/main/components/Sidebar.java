package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.MenuPrincipal;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import lombok.Getter;

import java.lang.invoke.SerializedLambda;
import java.util.function.Consumer;

@Getter
public class Sidebar extends VBox {
    private final SidebarButton botoInici;
    private final SidebarButton botoFactures;
    private final SidebarButton botoPressupostos;
    private final SidebarButton botoClients;
    private final SidebarButton botoArticles;
    private final SidebarButton botoIva;
    private final SidebarButton botoFormaPagament;
    private final SidebarButton botoGeografia;
    private final SidebarButton botoInformes;
    private final SidebarButton botoTasca;
    private final SidebarButton botoCopiaSeguretat;
    private final SidebarButton botoDadesEmpresa;
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
        botoIva = new SidebarButton("IVA", "iva.png");
        botoFormaPagament = new SidebarButton("Forma de pagament", "forma_pagament.png");
        botoGeografia = new SidebarButton("Geografia", "geografia.png");

        botoInformes = new SidebarButton("Informes", "informes.png");
        botoTasca = new SidebarButton("Tasques", "tasca.png");

        botoCopiaSeguretat = new SidebarButton("Còpia de seguretat", "copia_seguretat.png");
        botoDadesEmpresa = new SidebarButton("Dades empresa", "configuracio.png");
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
                botoIva,
                botoFormaPagament,
                botoInformes,
                botoTasca,
                botoGeografia,
                espai,
                botoCopiaSeguretat,
                botoDadesEmpresa,
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

        botoIva.setOnAction(e->{
            seleccionarBoto(botoIva);
            onMenuClick.accept(MenuPrincipal.IVA);
        });

        botoFormaPagament.setOnAction( e->{
            seleccionarBoto(botoFormaPagament);
            onMenuClick.accept(MenuPrincipal.FORMA_DE_PAGAMENT);
        });

        botoGeografia.setOnAction(e -> {
            seleccionarBoto(botoGeografia);
            onMenuClick.accept(MenuPrincipal.GEOGRAFIA);
        });

        botoInformes.setOnAction(e -> {
            seleccionarBoto(botoInformes);
            onMenuClick.accept(MenuPrincipal.INFORMES);
        });

        botoTasca.setOnAction(e -> {
            seleccionarBoto(botoTasca);
            onMenuClick.accept(MenuPrincipal.TASCA);
        });

        botoCopiaSeguretat.setOnAction(e -> {
            seleccionarBoto(botoCopiaSeguretat);
            onMenuClick.accept(MenuPrincipal.COPIA_SEGURETAT);
        });

        botoDadesEmpresa.setOnAction(e -> {
            seleccionarBoto(botoDadesEmpresa);
            onMenuClick.accept(MenuPrincipal.DADES_EMPRESA);
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
        botoIva.seleccionar(false);
        botoFormaPagament.seleccionar(false);
        botoGeografia.seleccionar(false);
        botoInformes.seleccionar(false);
        botoTasca.seleccionar(false);
        botoDadesEmpresa.seleccionar(false);
        botoSobre.seleccionar(false);
        seleccionat.seleccionar(true);
    }
}