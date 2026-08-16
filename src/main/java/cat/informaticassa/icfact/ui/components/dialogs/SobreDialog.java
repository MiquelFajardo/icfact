package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.BuildInfo;
import cat.informaticassa.icfact.ConstantsAplicacio;
import cat.informaticassa.icfact.actualitzacio.model.InformacioActualitzacio;
import cat.informaticassa.icfact.actualitzacio.service.ActualitzacioService;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class SobreDialog extends DialogBase {
    private final BotoPrimari botoActualitzar = new BotoPrimari("↻  Comprovar actualitzacions");
    private final BotoPrimari botoWhatsApp = new BotoPrimari("💬  WhatsApp");
    private final BotoPrimari botoEmail = new BotoPrimari("✉  Email");
    private static final Logger logger = LoggerFactory.getLogger(SobreDialog.class);
    private final ActualitzacioService actualitzacioService = new ActualitzacioService();

    public SobreDialog() {
        super("Sobre ICFact", 620, 560);
        inicialitzar();
    }

    private void inicialitzar() {
        getRoot().setStyle("-fx-background-color: #07101f; -fx-border-color: #1e293b; -fx-border-width: 1;");
        Label lblCapcalera = new Label("Sobre ICFact");
        lblCapcalera.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
        BorderPane capcalera = new BorderPane();
        capcalera.setCenter(lblCapcalera);
        capcalera.setPadding(new Insets(14, 20, 14, 20));
        capcalera.setStyle("-fx-background-color: #0a1426; -fx-border-color: #1e293b; -fx-border-width: 0 0 1 0;");
        getRoot().setTop(capcalera);
        Label lblLogo = new Label("ICFact");
        lblLogo.setStyle("-fx-font-size: 58px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(to right, #1683ff, #ffffff);");
        Label lblDescripcio = new Label("Facturació senzilla per autònoms");
        lblDescripcio.setStyle("-fx-font-size: 18px; -fx-text-fill: #aebbd0;");
        HBox separador = crearSeparador();
        Label lblVersio = new Label("Versió " + BuildInfo.getVersio());
        lblVersio.setStyle("-fx-font-size: 15px; -fx-text-fill: #aebbd0; -fx-background-color: #0d192b; -fx-border-color: #263750;" +
                "-fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 9 18 9 18;");
        botoActualitzar.setPrefWidth(380);
        botoActualitzar.setPrefHeight(52);
        botoActualitzar.setStyle("-fx-background-color: linear-gradient(to right, #1675f5, #3530f2); -fx-text-fill: white;" +
                "-fx-font-size: 17px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-border-radius: 12; -fx-cursor: hand;");
        botoWhatsApp.setPrefWidth(180);
        botoWhatsApp.setPrefHeight(48);
        botoWhatsApp.setStyle("-fx-background-color: #0d192b; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;" +
                "-fx-background-radius: 10; -fx-border-color: #263750; -fx-border-width: 1; -fx-border-radius: 10; -fx-cursor: hand;");
        botoEmail.setPrefWidth(180);
        botoEmail.setPrefHeight(48);
        botoEmail.setStyle("-fx-background-color: #0d192b; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;" +
                "-fx-background-radius: 10; -fx-border-color: #263750; -fx-border-width: 1; -fx-border-radius: 10; -fx-cursor: hand;");
        HBox contactes = new HBox(15, botoWhatsApp, botoEmail);
        contactes.setAlignment(Pos.CENTER);
        Label lblAutor = createAutor();
        VBox contingut = new VBox(18, lblLogo, lblDescripcio, separador, lblVersio, botoActualitzar, contactes, lblAutor);
        contingut.setAlignment(Pos.CENTER);
        contingut.setPadding(new Insets(25, 30, 25, 30));
        getRoot().setCenter(contingut);
        getBotons().getChildren().remove(getBotoCancelar());
        getBotoGuardar().setText("✕ Tancar");
        getBotoGuardar().setOnAction(e -> close());
        botoActualitzar.setOnAction(e -> comprovarActualitzacions());
        botoWhatsApp.setOnAction(e -> obrirWhatsApp());
        botoEmail.setOnAction(e -> obrirEmail());
    }


    private void comprovarActualitzacions() {
        InformacioActualitzacio informacio = actualitzacioService.comprovar();
        if (informacio == null) {
            Alerta.informacio(getScene().getWindow(),"Actualitzacions","Ja tens l'última versió d'ICFact.");
            return;
        }
        ActualitzacioDialog dialog = new ActualitzacioDialog(getScene().getWindow(), informacio);
        ActualitzacioDialog.Resultat resultat = dialog.mostrar();
        switch (resultat) {
            case ACTUALITZAR -> {
                String url;
                String nomFitxer;
                if (esWindows()) {
                    if (!informacio.getWindows().isDisponible()) {
                        Alerta.error(getScene().getWindow(),"No hi ha cap actualització disponible per a Windows.");
                        return;
                    }
                    url = informacio.getWindows().getUrl();
                    nomFitxer = "ICFact-Setup.exe";
                } else {
                    if (!informacio.getLinux().isDisponible()) {
                        Alerta.error(getScene().getWindow(),"No hi ha cap actualització disponible per a Linux.");
                        return;
                    }
                    url = informacio.getLinux().getUrl();
                    nomFitxer = "ICFact-Linux.tar.gz";
                }
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar actualització d'ICFact");
                fileChooser.setInitialFileName(nomFitxer);
                if (esWindows()) {
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Instal·lador d'ICFact (*.exe)","*.exe"));
                } else {
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Paquet Linux (*.tar.gz)", "*.tar.gz"));
                }
                File fitxer = fileChooser.showSaveDialog(getScene().getWindow());
                if (fitxer == null) {
                    return;
                }
                ActualitzacioDescarregaDialog descarrega = new ActualitzacioDescarregaDialog(getScene().getWindow());
                descarrega.show();
                descarrega.iniciarDescarga(url, fitxer.toPath());
            }
            case VEURE_NOTES -> {
                NotesActualitzacioDialog notesDialog = new NotesActualitzacioDialog(getScene().getWindow(), informacio.getUltimaVersio(), informacio.getNotes());
                notesDialog.mostrar();
            }
        }
    }

    private void obrirWhatsApp() {
        obrirProtocol("whatsapp://send?phone=" + ConstantsAplicacio.TELEFON);
    }

    private void obrirEmail() {
        obrirProtocol(
                "mailto:" + ConstantsAplicacio.EMAIL + "?subject=Suport%20ICFact"
        );
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
        } catch (Exception ex) {
            logger.error("No s'ha pogut obrir el protocol '{}'.", url, ex);
        }
    }

    private HBox crearSeparador() {
        Region esquerra = new Region();
        esquerra.setPrefWidth(110);
        esquerra.setPrefHeight(1);
        esquerra.setStyle("-fx-background-color: #263750;");
        Region punt = new Region();
        punt.setPrefSize(10, 10);
        punt.setMaxSize(10, 10);
        punt.setStyle("-fx-background-color: #1683ff; -fx-background-radius: 10;");
        Region dreta = new Region();
        dreta.setPrefWidth(110);
        dreta.setPrefHeight(1);
        dreta.setStyle("-fx-background-color: #263750;");
        HBox separador = new HBox(10, esquerra, punt, dreta);
        separador.setAlignment(Pos.CENTER);
        return separador;
    }

    private Label createAutor() {
        Label lblAutor = new Label("© 2026 Informaticassa");
        lblAutor.setStyle("-fx-font-size: 13px; -fx-text-fill: #71819a; -fx-cursor: hand;");
        lblAutor.setOnMouseEntered(e -> lblAutor.setStyle("-fx-font-size: 13px; -fx-text-fill: #1683ff; -fx-cursor: hand;"));
        lblAutor.setOnMouseExited(e -> lblAutor.setStyle("-fx-font-size: 13px; -fx-text-fill: #71819a; -fx-cursor: hand;"));
        lblAutor.setOnMouseClicked(e -> obrirProtocol(ConstantsAplicacio.WEB));
        return lblAutor;
    }

    private boolean esWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

}