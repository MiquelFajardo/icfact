package cat.informaticassa.icfact.ui.main.pagines;


import cat.informaticassa.icfact.BuildInfo;
import cat.informaticassa.icfact.ui.main.components.ActionCard;
import cat.informaticassa.icfact.ui.main.components.Card;
import cat.informaticassa.icfact.ui.main.components.InfoCard;
import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class PaginaInici extends VBox {

    public PaginaInici() {

        setSpacing(25);
        setPadding(new Insets(30));

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
        ActionCard novaFactura = new ActionCard("Nova factura", "factura_card.png");
        ActionCard pressupostosPendents = new ActionCard("Pressupostos pendents", "pressupost_pendent_card.png");
        ActionCard facturesPendents = new ActionCard("Factures pendents", "factura_pendent_card.png");
        ActionCard nouClient = new ActionCard("Nou client", "clients_card.png");
        ActionCard nouArticle = new ActionCard("Nou article", "articles_card.png");


        graellaAccions.add(nouPressupost, 0, 0);
        graellaAccions.add(novaFactura, 1, 0);

        graellaAccions.add(pressupostosPendents, 0, 1);
        graellaAccions.add(facturesPendents, 1, 1);

        graellaAccions.add(nouClient, 2, 0);
        graellaAccions.add(nouArticle, 2, 1);

        Card cardAccions = new Card(null, graellaAccions);

        //--------------------------------------------------
        // Resum
        //--------------------------------------------------

        GridPane graellaResum = new GridPane();
        graellaResum.setHgap(20);

        graellaResum.add(new InfoCard("Clients", 0), 0, 0);
        graellaResum.add(new InfoCard("Articles", 0), 1, 0);
        graellaResum.add(new InfoCard("Pressupostos", 0), 2, 0);
        graellaResum.add(new InfoCard("Factures", 0), 3, 0);

        Card cardResum = new Card("Resum", graellaResum);

        Region espai = new Region();
        VBox.setVgrow(espai, Priority.ALWAYS);

        Label versio = new Label("Versió " + BuildInfo.getVersio());
        versio.setStyle("-fx-text-fill: gray;");

        getChildren().addAll(
                titol,
                subtitol,
                cardAccions,
                cardResum,
                espai,
                versio
        );
    }
}