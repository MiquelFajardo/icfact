package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.ConstantsAplicacio;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.service.RecuperarContrasenyaService;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.PrimaryButton;
import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecuperarContrasenyaDialog extends Stage {
    private final Empresa empresa;

    private final TextField clau = new TextField();
    private final PasswordField novaContrasenya = new PasswordField();
    private final PasswordField repetirContrasenya = new PasswordField();
    private final PrimaryButton botoCanviar = new PrimaryButton("Canviar contrasenya");

    public RecuperarContrasenyaDialog(Empresa empresa) {
        this.empresa = empresa;
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Recuperar contrasenya");
        inicialitzar();
    }

    private void inicialitzar() {
        VBox root = new VBox(16);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(35, 45, 35, 45));

        root.setStyle("""
                -fx-background-color: #071222;
                -fx-border-color: #26354d;
                -fx-border-width: 1;
                """);

        Label titol = new Label("Recuperar contrasenya");
        titol.setStyle("""
                -fx-font-size: 25px;
                -fx-font-weight: bold;
                -fx-text-fill: white;
                """);

        Label info = new Label("Introdueix la clau de recuperació que es va mostrar quan es va crear l'empresa.");
        info.setWrapText(true);
        info.setTextFill(Color.web("#9fb0c8"));
        info.setStyle("-fx-font-size: 14px;");

        clau.setPromptText("ICF-XXXX-XXXX-XXXX-XXXX");
        clau.setMaxWidth(360);

        novaContrasenya.setPromptText("Nova contrasenya");
        novaContrasenya.setMaxWidth(360);

        repetirContrasenya.setPromptText("Repetir contrasenya");
        repetirContrasenya.setMaxWidth(360);

        botoCanviar.setMaxWidth(360);
        botoCanviar.setStyle("""
                -fx-background-color: linear-gradient(to right, #1687ff, #3932ff);
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-background-radius: 12px;
                -fx-padding: 13px 20px;
                -fx-cursor: hand;
                """);

        Label ajuda = new Label("No tens la clau de recuperació?\nPosa't en contacte amb Informaticassa.");
        ajuda.setStyle("""
        -fx-font-size: 14px;
        -fx-font-weight: bold;
        -fx-text-fill: #cbd5e1;
        -fx-alignment: center;
        """);

        HBox contacte = crearContacte();
        botoCanviar.setOnAction(e -> canviarContrasenya());
        root.getChildren().addAll(
                titol,
                info,
                clau,
                novaContrasenya,
                repetirContrasenya,
                botoCanviar,
                ajuda,
                contacte
        );
        Scene scene = new Scene(root, 520, 480);
        scene.setFill(Color.web("#071222"));
        setScene(scene);
        setResizable(false);
        centerOnScreen();
    }

    private void canviarContrasenya() {
        String clauRecuperacio = clau.getText().trim();
        String nova = novaContrasenya.getText();
        String repetir = repetirContrasenya.getText();
        if (clauRecuperacio.isBlank()) {
            Alerta.error(this,  "Has d'introduir la clau de recuperació.");
            return;
        }
        if (nova.isBlank()) {
            Alerta.error(this,"Has d'introduir la nova contrasenya.");
            return;
        }
        if (!nova.equals(repetir)) {
            Alerta.error(this, "Les contrasenyes no coincideixen.");
            return;
        }
        try {
            RecuperarContrasenyaService service = new RecuperarContrasenyaService();
            boolean correcte = service.executar(empresa, clauRecuperacio, nova);
            if (!correcte) {
                Alerta.error(this,"La clau de recuperació no és correcta.");
                clau.clear();
                clau.requestFocus();
                return;
            }
            Alerta.informacio(this, "Contrasenya actualitzada","La contrasenya s'ha canviat correctament.");
            close();

        } catch (Exception e) {
            Alerta.error(this,"No s'ha pogut canviar la contrasenya.");
        }
    }

    private HBox crearContacte() {
        BotoPrimari botoWhatsApp = new BotoPrimari("💬  WhatsApp");
        BotoPrimari botoEmail = new BotoPrimari("✉  Email");
        botoWhatsApp.setPrefWidth(170);
        botoWhatsApp.setPrefHeight(45);
        botoEmail.setPrefWidth(170);
        botoEmail.setPrefHeight(45);
        botoWhatsApp.setStyle("""
            -fx-background-color: #0d192b;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 10;
            -fx-border-color: #263750;
            -fx-border-width: 1;
            -fx-border-radius: 10;
            -fx-cursor: hand;
            """);

        botoEmail.setStyle("""
            -fx-background-color: #0d192b;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 10;
            -fx-border-color: #263750;
            -fx-border-width: 1;
            -fx-border-radius: 10;
            -fx-cursor: hand;
            """);
        botoWhatsApp.setOnAction(e -> obrirProtocol( "whatsapp://send?phone=" + ConstantsAplicacio.TELEFON));
        botoEmail.setOnAction(e ->
                obrirProtocol("mailto:" + ConstantsAplicacio.EMAIL + "?subject=Recuperar%20contrasenya%20ICFact"));
        HBox contacte = new HBox(12, botoWhatsApp, botoEmail);
        contacte.setAlignment(Pos.CENTER);
        return contacte;
    }

    private void obrirProtocol(String url) {
        try {
            String sistema = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;
            if (sistema.contains("win")) {
                processBuilder = new ProcessBuilder("cmd", "/c", "start", "", url);
            } else if (sistema.contains("mac")) {
                processBuilder = new ProcessBuilder("open", url);
            } else {
                processBuilder = new ProcessBuilder("xdg-open", url);
            }
            processBuilder.start();
        } catch (Exception e) {
            Alerta.error(this,"No s'ha pogut obrir l'aplicació de contacte.");
        }
    }
}