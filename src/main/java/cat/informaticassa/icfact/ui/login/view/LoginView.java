package cat.informaticassa.icfact.ui.login.view;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.components.FooterInformaticassa;
import cat.informaticassa.icfact.ui.components.PasswordFieldBox;
import cat.informaticassa.icfact.ui.components.PrimaryButton;
import cat.informaticassa.icfact.ui.login.controller.LoginController;
import cat.informaticassa.icfact.ui.navigation.Navegador;
import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        setBackground(new Background(new BackgroundFill(Color.web("#071222"), CornerRadii.EMPTY, Insets.EMPTY)));

        VBox card = new VBox(18);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40, 55, 35, 55));
        card.setMaxWidth(620);
        card.setMinWidth(620);
        card.setBackground(new Background(new BackgroundFill(Color.web("#071222"), new CornerRadii(16), Insets.EMPTY)));
        card.setBorder(new Border(new BorderStroke(Color.web("#26354d"), BorderStrokeStyle.SOLID, new CornerRadii(16), new BorderWidths(1))));

        Label nomEmpresa = new Label(empresa.getNom());
        nomEmpresa.setFont(Font.font("System", 30));
        nomEmpresa.setTextFill(Color.WHITE);
        nomEmpresa.setStyle("""
            -fx-font-weight: bold;
        """);
        Label descripcio = new Label(empresa.getDescripcio() != null ? empresa.getDescripcio() : "");
        descripcio.setFont(Font.font("System", 16));
        descripcio.setTextFill(Color.web("#9fb0c8"));

        Label titol = new Label("Accés a ICFact");
        titol.setFont(Font.font("System", 26));
        titol.setTextFill(Color.WHITE);
        titol.setStyle("""
            -fx-font-weight: bold;
        """);

        Label info = new Label("Introdueix la contrasenya per accedir a ICFact.");
        info.setFont(Tema.TEXT_NORMAL);
        info.setTextFill(Color.web("#9fb0c8"));

        contrasenya.setMaxWidth(320);

        botoEntrar.setMaxWidth(320);
        botoEntrar.setStyle("""
            -fx-background-color: linear-gradient(to right, #1687ff, #3932ff);
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-background-radius: 12px;
            -fx-padding: 13px 20px;
            -fx-cursor: hand;
        """);

        recuperar.setFont(Tema.TEXT_NORMAL);
        recuperar.setTextFill(Color.web("#1687ff"));
        recuperar.setStyle("""
            -fx-text-fill: #1687ff;
            -fx-border-color: transparent;
            -fx-padding: 5px;
        """);

        Region separador = new Region();
        separador.setPrefHeight(1);
        separador.setMaxWidth(420);
        separador.setBackground(new Background(new BackgroundFill(Color.web("#26354d"), CornerRadii.EMPTY, Insets.EMPTY)));
        FooterInformaticassa footer = new FooterInformaticassa();

        card.getChildren().addAll(
                nomEmpresa,
                descripcio,
                separador,
                titol,
                info,
                contrasenya,
                botoEntrar,
                recuperar,
                footer
        );
        StackPane contenidor = new StackPane(card);
        contenidor.setAlignment(Pos.CENTER);
        contenidor.setPadding(new Insets(30));
        setCenter(contenidor);
        new LoginController(this, navegador, empresa);
    }
}