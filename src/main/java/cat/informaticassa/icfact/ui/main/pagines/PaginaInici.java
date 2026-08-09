package cat.informaticassa.icfact.ui.main.pagines;


import cat.informaticassa.icfact.BuildInfo;
import cat.informaticassa.icfact.ui.components.Card;
import cat.informaticassa.icfact.ui.components.dialogs.ClientDialog;
import cat.informaticassa.icfact.ui.components.dialogs.FacturaDialog;
import cat.informaticassa.icfact.ui.components.dialogs.PressupostDialog;
import cat.informaticassa.icfact.ui.components.dialogs.ProducteDialog;
import cat.informaticassa.icfact.ui.main.components.ActionCard;
import cat.informaticassa.icfact.ui.main.components.TasquesPendentsCardView;
import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Setter;

public class PaginaInici extends VBox {
    private Runnable onFacturesPendents;

    public PaginaInici() {
        setSpacing(25);
        setPadding(new Insets(30));
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setFillWidth(true);

        Label titol = new Label("Benvingut a ICFact");
        titol.setFont(Tema.TITOL);

        Label subtitol = new Label("La manera més senzilla de gestionar pressupostos i factures.");
        subtitol.setFont(Tema.SUBTITOL);

        //--------------------------------------------------
        // Accions ràpides
        //--------------------------------------------------

        GridPane graellaAccions = new GridPane();
        graellaAccions.setAlignment(Pos.CENTER);
        graellaAccions.setHgap(20);
        graellaAccions.setVgap(20);

        ActionCard nouPressupost = new ActionCard("Nou pressupost", "pressupost_card.png");
        nouPressupost.setOnMouseClicked( e-> {
            PressupostDialog dialog = new PressupostDialog();
            dialog.initOwner((Stage) getScene().getWindow());
            dialog.showAndWait();
        });

        ActionCard novaFactura = new ActionCard("Nova factura", "factura_card.png");
        novaFactura.setOnMouseClicked(e -> {
            FacturaDialog dialog = new FacturaDialog();
            dialog.initOwner((Stage) getScene().getWindow());
            dialog.showAndWait();
        });

        ActionCard pressupostosPendents = new ActionCard("Pressupostos pendents", "pressupost_pendent_card.png");



        ActionCard facturesPendents = new ActionCard("Factures pendents", "factura_pendent_card.png");
        facturesPendents.setOnMouseClicked(e -> {
            if (onFacturesPendents != null) {
                onFacturesPendents.run();
            }
        });

        ActionCard nouClient = new ActionCard("Nou client", "clients_card.png");
        nouClient.setOnMouseClicked(e -> {
            ClientDialog dialog = new ClientDialog();
            dialog.initOwner((Stage) getScene().getWindow());
            dialog.showAndWait();
        });

        ActionCard nouArticle = new ActionCard("Nou producte", "articles_card.png");
        nouArticle.setOnMouseClicked(e -> {
            ProducteDialog dialog = new ProducteDialog();
            dialog.initOwner((Stage) getScene().getWindow());
            dialog.showAndWait();
        });

        graellaAccions.add(nouPressupost, 0, 0);
        graellaAccions.add(novaFactura, 1, 0);

        graellaAccions.add(pressupostosPendents, 0, 1);
        graellaAccions.add(facturesPendents, 1, 1);

        graellaAccions.add(nouClient, 2, 0);
        graellaAccions.add(nouArticle, 2, 1);

       Card cardAccions = new Card(null, graellaAccions);

        //--------------------------------------------------
        // Tasques
        //--------------------------------------------------

        Card cardTasques = new Card("Tasques pendents",new TasquesPendentsCardView());

        // Visualització
        Region espai = new Region();
        VBox.setVgrow(espai, Priority.ALWAYS);
        Label versio = new Label("Versió " + BuildInfo.getVersio());
        versio.setStyle("-fx-text-fill: gray;");
        getChildren().addAll(
                titol,
                subtitol,
                cardAccions,
                cardTasques,
                espai,
                versio
        );
    }

    public void setOnFacturesPendents(Runnable onFacturesPendents) {
        this.onFacturesPendents = onFacturesPendents;
    }
}