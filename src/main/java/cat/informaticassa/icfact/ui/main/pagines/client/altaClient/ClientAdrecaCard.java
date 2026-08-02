package cat.informaticassa.icfact.ui.main.pagines.client.altaClient;

import cat.informaticassa.icfact.ui.components.BotoSecundari;
import cat.informaticassa.icfact.ui.components.Card;
import cat.informaticassa.icfact.ui.components.FormField;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class ClientAdrecaCard extends Card {

    private final FormField carrer = new FormField("🏠 Carrer");
    private final FormField numero = new FormField("🔢 Número");
    private final FormField pis = new FormField("🚪 Pis");
    private final FormField porta = new FormField("🪪 Porta");
    private final FormField codiPostal = new FormField("📮 Codi postal");

    private final ComboBox<String> cmbPoblacio = new ComboBox<>();

    private final BotoSecundari botoNovaPoblacio =
            new BotoSecundari("Nova població");

    public ClientAdrecaCard() {

        super("Adreça");

        carrer.setPromptText("Carrer");
        numero.setPromptText("Número");
        pis.setPromptText("Pis");
        porta.setPromptText("Porta");
        codiPostal.setPromptText("Codi postal");

        cmbPoblacio.setPromptText("Selecciona una població");
        cmbPoblacio.setMaxWidth(Double.MAX_VALUE);

        Label lblPoblacio = new Label("📍 Població");

        VBox poblacio = new VBox(6);

        poblacio.getChildren().addAll(
                lblPoblacio,
                cmbPoblacio,
                botoNovaPoblacio
        );

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10));
        grid.setHgap(20);
        grid.setVgap(20);

        grid.add(carrer,0,0,2,1);

        grid.add(numero,0,1);
        grid.add(codiPostal,1,1);

        grid.add(pis,0,2);
        grid.add(porta,1,2);

        grid.add(poblacio,0,3,2,1);

        GridPane.setHgrow(carrer, Priority.ALWAYS);
        GridPane.setHgrow(numero, Priority.ALWAYS);
        GridPane.setHgrow(codiPostal, Priority.ALWAYS);
        GridPane.setHgrow(pis, Priority.ALWAYS);
        GridPane.setHgrow(porta, Priority.ALWAYS);
        GridPane.setHgrow(poblacio, Priority.ALWAYS);

        afegir(grid);
    }
}