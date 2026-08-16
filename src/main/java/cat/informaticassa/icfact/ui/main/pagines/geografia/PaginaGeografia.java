package cat.informaticassa.icfact.ui.main.pagines.geografia;

import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.Card;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginaGeografia extends BorderPane {
    private final ComboBox<String> cmbPais = new ComboBox<>();
    private final ComboBox<String> cmbProvincia = new ComboBox<>();
    private final ComboBox<String> cmbPoblacio = new ComboBox<>();
    private final BotoPrimari botoNouPais = new BotoPrimari("Nou país");
    private final BotoPrimari botoModificarPais = new BotoPrimari("Modificar");
    private final BotoPrimari botoNovaProvincia = new BotoPrimari("Nova província");
    private final BotoPrimari botoModificarProvincia = new BotoPrimari("Modificar");
    private final BotoPrimari botoNovaPoblacio = new BotoPrimari("Nova població");
    private final BotoPrimari botoModificarPoblacio = new BotoPrimari("Modificar");
    private final GeografiaController controller;

    public PaginaGeografia() {
        setPadding(new Insets(20));
        VBox contingut = new VBox(20);
        contingut.getChildren().addAll(crearCardPais(), crearCardProvincia(), crearCardPoblacio());
        setCenter(contingut);
        cmbProvincia.setDisable(true);
        botoNovaProvincia.setDisable(true);
        botoModificarProvincia.setDisable(true);
        cmbPoblacio.setDisable(true);
        botoNovaPoblacio.setDisable(true);
        botoModificarPoblacio.setDisable(true);
        controller = new GeografiaController(this);
    }

    public void refrescar() {
        controller.carregarPaisos();
    }

    private Card crearCardPais() {
        Card card = new Card("Països");
        HBox botons = new HBox(
                10,
                botoNouPais,
                botoModificarPais
        );
        VBox box = new VBox(
                15,
                cmbPais,
                botons
        );
        VBox.setVgrow(cmbPais, Priority.NEVER);
        card.afegir(box);
        return card;
    }

    private Card crearCardProvincia() {
        Card card = new Card("Províncies");
        HBox botons = new HBox(
                10,
                botoNovaProvincia,
                botoModificarProvincia
        );
        VBox box = new VBox(
                15,
                cmbProvincia,
                botons
        );
        card.afegir(box);
        return card;
    }

    private Card crearCardPoblacio() {
        Card card = new Card("Poblacions");
        HBox botons = new HBox(
                10,
                botoNovaPoblacio,
                botoModificarPoblacio
        );
        VBox box = new VBox(
                15,
                cmbPoblacio,
                botons
        );
        card.afegir(box);
        return card;
    }
}