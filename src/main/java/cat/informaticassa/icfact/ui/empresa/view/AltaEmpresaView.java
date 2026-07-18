package cat.informaticassa.icfact.ui.empresa.view;

import cat.informaticassa.icfact.ui.components.AppHeader;
import cat.informaticassa.icfact.ui.components.Card;
import cat.informaticassa.icfact.ui.components.FooterInformaticassa;
import cat.informaticassa.icfact.ui.components.FormField;
import cat.informaticassa.icfact.ui.components.PasswordFieldBox;
import cat.informaticassa.icfact.ui.components.PrimaryButton;
import cat.informaticassa.icfact.ui.empresa.controller.AltaEmpresaController;
import cat.informaticassa.icfact.ui.navigation.Navegador;
import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import lombok.Getter;

@Getter
public class AltaEmpresaView extends BorderPane {
    private final Navegador navegador;
    private final FormField nomEmpresa = new FormField("Nom de l'empresa *");
    private final FormField nif = new FormField("NIF *");
    private final FormField descripcio = new FormField("Descripció");
    private final FormField telefon = new FormField("Telèfon");
    private final FormField email = new FormField("Correu electrònic");
    private final PasswordFieldBox contrasenya = new PasswordFieldBox("Contrasenya *");
    private final PasswordFieldBox repetirContrasenya = new PasswordFieldBox("Repetir contrasenya *");
    private final PrimaryButton botoCrear = new PrimaryButton("Crear empresa");

    public AltaEmpresaView(Navegador navegador) {
        this.navegador = navegador;
        inicialitzar();
    }

    private void inicialitzar() {
        setBackground(new Background(new BackgroundFill(Tema.FONS, CornerRadii.EMPTY, Insets.EMPTY)));
        AppHeader header = new AppHeader("Benvingut!","Configurarem la teva empresa en menys de dos minuts.");

        Card card = new Card();

        GridPane formulari = new GridPane();
        formulari.setHgap(20);
        formulari.setVgap(18);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);

        formulari.getColumnConstraints().addAll(c1, c2);

        formulari.add(nomEmpresa, 0, 0);
        GridPane.setColumnSpan(nomEmpresa, 2);

        formulari.add(descripcio, 0, 1);
        GridPane.setColumnSpan(descripcio, 2);

        formulari.add(nif, 0, 2);
        formulari.add(telefon, 1, 2);

        formulari.add(email, 0, 3, 2, 1);

        formulari.add(contrasenya, 0, 4);
        formulari.add(repetirContrasenya, 1, 4);

        GridPane.setHgrow(nomEmpresa, Priority.ALWAYS);
        GridPane.setHgrow(nif, Priority.ALWAYS);
        GridPane.setHgrow(descripcio, Priority.ALWAYS);
        GridPane.setHgrow(telefon, Priority.ALWAYS);
        GridPane.setHgrow(email, Priority.ALWAYS);
        GridPane.setHgrow(contrasenya, Priority.ALWAYS);
        GridPane.setHgrow(repetirContrasenya, Priority.ALWAYS);

        Label obligatoris = new Label("Els camps marcats amb * són obligatoris.");
        obligatoris.setFont(Tema.TEXT_NORMAL);
        obligatoris.setTextFill(Tema.TEXT_SECUNDARI);
        obligatoris.setMaxWidth(Double.MAX_VALUE);
        obligatoris.setAlignment(Pos.CENTER_LEFT);

        FooterInformaticassa footer = new FooterInformaticassa();

        card.add(header);
        card.add(formulari);
        card.add(obligatoris);
        card.add(botoCrear);
        card.add(footer);

        botoCrear.setMaxWidth(Double.MAX_VALUE);

        StackPane contenidor = new StackPane(card);
        contenidor.setAlignment(Pos.CENTER);

        setCenter(contenidor);

        BorderPane.setAlignment(card, Pos.CENTER);
        BorderPane.setMargin(card, new Insets(15));

        new AltaEmpresaController(this, navegador);

    }

}