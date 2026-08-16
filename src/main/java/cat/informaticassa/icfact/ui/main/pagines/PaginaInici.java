package cat.informaticassa.icfact.ui.main.pagines;

import cat.informaticassa.icfact.BuildInfo;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import cat.informaticassa.icfact.ui.components.Card;
import cat.informaticassa.icfact.ui.components.dialogs.ClientDialog;
import cat.informaticassa.icfact.ui.components.dialogs.FacturaDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PressupostDialog;
import cat.informaticassa.icfact.ui.components.dialogs.ProducteDialog;
import cat.informaticassa.icfact.ui.main.components.ActionCard;
import cat.informaticassa.icfact.ui.main.components.MetricCard;
import cat.informaticassa.icfact.ui.main.components.TasquesPendentsCardView;
import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Setter;

import java.time.LocalDate;

public class PaginaInici extends VBox {

    @Setter
    private Runnable onFacturesPendents;

    private final TasquesPendentsCardView tasquesPendents =
            new TasquesPendentsCardView();

    private final PressupostRepository pressupostRepository =
            new PressupostRepository();

    private final FacturaRepository facturaRepository =
            new FacturaRepository();

    private final ClientRepository clientRepository =
            new ClientRepository();

    private final HBox estadistiques = new HBox(15);

    public PaginaInici() {

        setSpacing(18);
        setPadding(new Insets(30));
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setFillWidth(true);

        Label titol = new Label("Benvingut a ICFact");
        titol.setFont(Tema.TITOL);

        Label subtitol = new Label(
                "La manera més senzilla de gestionar pressupostos i factures."
        );
        subtitol.setFont(Tema.SUBTITOL);
        subtitol.setStyle("""
                -fx-text-fill: #64748B;
                """);

        // ==========================================================
        // ESTADÍSTIQUES
        // ==========================================================

        crearEstadistiques();

        // ==========================================================
        // ACCIONS RÀPIDES
        // ==========================================================

        Label titolAccions = new Label("Accions ràpides");
        titolAccions.setFont(Tema.TITOL_CARD);
        titolAccions.setStyle("""
                -fx-font-weight: bold;
                -fx-text-fill: #111827;
                """);

        HBox accions = crearAccions();

        // ==========================================================
        // TASQUES
        // ==========================================================

        Card cardTasques = new Card(
                "Tasques pendents",
                tasquesPendents
        );

        // ==========================================================
        // PEU
        // ==========================================================

        Region espai = new Region();
        VBox.setVgrow(espai, Priority.ALWAYS);

        Label versio = new Label(
                "Versió " + BuildInfo.getVersio()
        );

        versio.setStyle("""
                -fx-font-family: "Inter";
                -fx-font-size: 12px;
                -fx-text-fill: #94A3B8;
                """);

        getChildren().addAll(
                titol,
                subtitol,
                estadistiques,
                titolAccions,
                accions,
                cardTasques,
                espai,
                versio
        );
    }

    private void crearEstadistiques() {

        estadistiques.setAlignment(Pos.CENTER_LEFT);
        estadistiques.setFillHeight(true);
        estadistiques.setMaxWidth(Double.MAX_VALUE);

        LocalDate avui = LocalDate.now();
        LocalDate inici = avui.minusDays(29);

        long pressupostos = pressupostRepository.buscarActius()
                .stream()
                .filter(p ->
                        p.getData() != null &&
                                !p.getData().isBefore(inici) &&
                                !p.getData().isAfter(avui)
                )
                .count();

        long factures = facturaRepository.buscarTots()
                .stream()
                .filter(f ->
                        f.getData() != null &&
                                !f.getData().isBefore(inici) &&
                                !f.getData().isAfter(avui)
                )
                .count();

        long clients = clientRepository.buscarTots().size();

        long pendents = facturaRepository.buscarPendents().size();

        MetricCard cardPressupostos = new MetricCard(
                "Pressupostos",
                String.valueOf(pressupostos),
                "Últims 30 dies",
                "pressupost_card.png",
                "#E8F1FF",
                "#3B82F6"
        );

        MetricCard cardFactures = new MetricCard(
                "Factures",
                String.valueOf(factures),
                "Últims 30 dies",
                "factura_card.png",
                "#E8F8F0",
                "#22C55E"
        );

        MetricCard cardClients = new MetricCard(
                "Clients",
                String.valueOf(clients),
                "Total registrats",
                "clients_card.png",
                "#F1EAFE",
                "#8B5CF6"
        );

        MetricCard cardPendents = new MetricCard(
                "Factures pendents",
                String.valueOf(pendents),
                "Import encara pendent",
                "factura_pendent_card.png",
                "#FEECEC",
                "#EF4444"
        );

        HBox.setHgrow(cardPressupostos, Priority.ALWAYS);
        HBox.setHgrow(cardFactures, Priority.ALWAYS);
        HBox.setHgrow(cardClients, Priority.ALWAYS);
        HBox.setHgrow(cardPendents, Priority.ALWAYS);

        estadistiques.getChildren().setAll(
                cardPressupostos,
                cardFactures,
                cardClients,
                cardPendents
        );
    }

    private HBox crearAccions() {

        HBox accions = new HBox(14);
        accions.setAlignment(Pos.CENTER);
        accions.setFillHeight(true);
        accions.setMaxWidth(Double.MAX_VALUE);

        ActionCard nouPressupost =
                new ActionCard(
                        "Nou pressupost",
                        "pressupost_card.png"
                );

        nouPressupost.setOnMouseClicked(e -> {
            PressupostDialog dialog = new PressupostDialog();
            dialog.initOwner(getScene().getWindow());
            dialog.showAndWait();
        });

        ActionCard novaFactura =
                new ActionCard(
                        "Nova factura",
                        "factura_card.png"
                );

        novaFactura.setOnMouseClicked(e -> {
            FacturaDialog dialog = new FacturaDialog();
            dialog.initOwner(getScene().getWindow());
            dialog.showAndWait();
        });

        ActionCard nouClient =
                new ActionCard(
                        "Nou client",
                        "clients_card.png"
                );

        nouClient.setOnMouseClicked(e -> {
            ClientDialog dialog = new ClientDialog();
            dialog.initOwner(getScene().getWindow());
            dialog.showAndWait();
        });

        ActionCard nouProducte =
                new ActionCard(
                        "Nou producte",
                        "articles_card.png"
                );

        nouProducte.setOnMouseClicked(e -> {
            ProducteDialog dialog = new ProducteDialog();
            dialog.initOwner(getScene().getWindow());
            dialog.showAndWait();
        });

        ActionCard facturesPendents =
                new ActionCard(
                        "Factures pendents",
                        "factura_pendent_card.png"
                );

        facturesPendents.setOnMouseClicked(e -> {
            if (onFacturesPendents != null) {
                onFacturesPendents.run();
            }
        });

        HBox.setHgrow(nouPressupost, Priority.ALWAYS);
        HBox.setHgrow(novaFactura, Priority.ALWAYS);
        HBox.setHgrow(nouClient, Priority.ALWAYS);
        HBox.setHgrow(nouProducte, Priority.ALWAYS);
        HBox.setHgrow(facturesPendents, Priority.ALWAYS);

        accions.getChildren().addAll(
                nouPressupost,
                novaFactura,
                nouClient,
                nouProducte,
                facturesPendents
        );

        return accions;
    }

    public void refrescarTasques() {
        tasquesPendents.carregar();
        crearEstadistiques();
    }
}