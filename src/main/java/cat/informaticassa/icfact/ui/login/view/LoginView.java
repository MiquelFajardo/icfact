package cat.informaticassa.icfact.ui.login.view;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.components.*;
import cat.informaticassa.icfact.ui.login.controller.LoginController;
import cat.informaticassa.icfact.ui.navigation.Navegador;
import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import lombok.Getter;

@Getter
public class LoginView extends BorderPane {
    private final Navegador navegador;
    private final Empresa empresa;

    private final PasswordFieldBox contrasenya = new PasswordFieldBox("Contrasenya");
    private final PrimaryButton botoEntrar = new PrimaryButton("Entrar");
    private final Hyperlink recuperar = new Hyperlink("He oblidat la contrasenya");

    public LoginView(Navegador navegador, Empresa empresa) {
        this.navegador = navegador;
        this.empresa = empresa;
        inicialitzar();
    }

    private void inicialitzar() {
        setBackground(new Background(new BackgroundFill(Tema.FONS, CornerRadii.EMPTY, Insets.EMPTY)));
        AppHeader header = new AppHeader(empresa.getNom(), empresa.getDescripcio());

        Card card = new Card();
        Label info = new Label("Introdueix la contrasenya per accedir a ICFact.");
        info.setFont(Tema.TEXT_NORMAL);
        info.setTextFill(Tema.TEXT_SECUNDARI);
        recuperar.setFont(Tema.TEXT_NORMAL);
        contrasenya.setMaxWidth(320);
        botoEntrar.setMaxWidth(320);
        card.add(header);
        card.add(info);
        card.add(contrasenya);
        card.add(botoEntrar);
        card.add(recuperar);
        card.add(new FooterInformaticassa());
        StackPane contenidor = new StackPane(card);
        contenidor.setAlignment(Pos.CENTER);
        setCenter(contenidor);
        BorderPane.setMargin(contenidor, new Insets(15));
        new LoginController(this, navegador, empresa);
    }
}