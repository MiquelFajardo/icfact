package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.BotoSecundari;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ClauRecuperacioDialog extends Stage {

    private final String clauRecuperacio;
    private final String nomEmpresa;

    private final BorderPane root = new BorderPane();

    public ClauRecuperacioDialog(
            String nomEmpresa,
            String clauRecuperacio
    ) {
        this.nomEmpresa = nomEmpresa;
        this.clauRecuperacio = clauRecuperacio;

        initModality(Modality.APPLICATION_MODAL);
        setTitle("Clau de recuperació");
        setWidth(620);
        setHeight(470);
        setResizable(false);

        inicialitzar();

        centerOnScreen();
    }

    private void inicialitzar() {

        root.setStyle("""
                -fx-background-color: #07101f;
                -fx-border-color: #1e293b;
                -fx-border-width: 1;
                """);

        // ---------------------------------------------------------
        // CAPÇALERA
        // ---------------------------------------------------------

        Label lblCapcalera = new Label("Clau de recuperació");

        lblCapcalera.setStyle("""
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-text-fill: white;
                """);

        BorderPane capcalera = new BorderPane();
        capcalera.setCenter(lblCapcalera);
        capcalera.setPadding(new Insets(14, 20, 14, 20));

        capcalera.setStyle("""
                -fx-background-color: #0a1426;
                -fx-border-color: #1e293b;
                -fx-border-width: 0 0 1 0;
                """);

        root.setTop(capcalera);

        // ---------------------------------------------------------
        // CONTINGUT
        // ---------------------------------------------------------

        Label lblTitol = new Label("Empresa creada correctament");

        lblTitol.setStyle("""
                -fx-font-size: 24px;
                -fx-font-weight: bold;
                -fx-text-fill: white;
                """);

        Label lblEmpresa = new Label(nomEmpresa);

        lblEmpresa.setStyle("""
                -fx-font-size: 16px;
                -fx-text-fill: #aebbd0;
                """);

        Label lblExplicacio = new Label(
                "Aquesta és la clau que et permetrà recuperar l'accés " +
                        "si oblides la contrasenya."
        );

        lblExplicacio.setWrapText(true);
        lblExplicacio.setMaxWidth(500);

        lblExplicacio.setStyle("""
                -fx-font-size: 15px;
                -fx-text-fill: #aebbd0;
                """);

        // ---------------------------------------------------------
        // CLAU
        // ---------------------------------------------------------

        TextField txtClau = new TextField(clauRecuperacio);

        txtClau.setEditable(false);
        txtClau.setFocusTraversable(false);
        txtClau.setAlignment(Pos.CENTER);

        txtClau.setStyle("""
                -fx-background-color: #0d192b;
                -fx-border-color: #1683ff;
                -fx-border-width: 1.5;
                -fx-border-radius: 10;
                -fx-background-radius: 10;
                -fx-text-fill: white;
                -fx-font-size: 22px;
                -fx-font-weight: bold;
                -fx-padding: 14px;
                """);

        txtClau.setPrefWidth(500);
        txtClau.setPrefHeight(58);

        Label lblAvis = new Label(
                "Guarda aquesta clau en un lloc segur. " +
                        "No es pot recuperar si la perds."
        );

        lblAvis.setStyle("""
                -fx-font-size: 13px;
                -fx-text-fill: #71819a;
                """);

        // ---------------------------------------------------------
        // BOTONS
        // ---------------------------------------------------------

        BotoSecundari botoGuardar = new BotoSecundari("💾  Guardar");
        BotoSecundari botoImprimir = new BotoSecundari("🖨  Imprimir");
        BotoPrimari botoContinuar = new BotoPrimari("Continuar");

        botoGuardar.setPrefWidth(150);
        botoGuardar.setPrefHeight(46);

        botoImprimir.setPrefWidth(150);
        botoImprimir.setPrefHeight(46);

        botoContinuar.setPrefWidth(150);
        botoContinuar.setPrefHeight(46);

        HBox botons = new HBox(
                12,
                botoGuardar,
                botoImprimir,
                botoContinuar
        );

        botons.setAlignment(Pos.CENTER);

        // ---------------------------------------------------------
        // ACCIONS
        // ---------------------------------------------------------

        botoGuardar.setOnAction(e -> guardar());

        botoImprimir.setOnAction(e -> imprimir());

        botoContinuar.setOnAction(e -> close());

        VBox contingut = new VBox(
                16,
                lblTitol,
                lblEmpresa,
                lblExplicacio,
                txtClau,
                lblAvis,
                botons
        );

        contingut.setAlignment(Pos.CENTER);
        contingut.setPadding(new Insets(30, 40, 30, 40));

        root.setCenter(contingut);

        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.web("#07101f"));

        setScene(scene);
    }

    // =============================================================
    // GUARDAR
    // =============================================================

    private void guardar() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Guardar clau de recuperació");

        fileChooser.setInitialFileName(
                "clau-recuperacio-ICFact.txt"
        );

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Fitxer de text (*.txt)",
                        "*.txt"
                )
        );

        File fitxer = fileChooser.showSaveDialog(this);

        if (fitxer == null) {
            return;
        }

        try {

            String contingut = """
                    ICFact
                    Clau de recuperació
                    ====================
                    
                    Empresa:
                    %s
                    
                    Clau de recuperació:
                    %s
                    
                    Guarda aquesta clau en un lloc segur.
                    Aquesta clau permet recuperar l'accés a ICFact
                    si oblides la contrasenya.
                    """.formatted(
                    nomEmpresa,
                    clauRecuperacio
            );

            Files.writeString(
                    fitxer.toPath(),
                    contingut,
                    StandardCharsets.UTF_8
            );

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    // =============================================================
    // IMPRIMIR
    // =============================================================

    private void imprimir() {

        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob == null) {
            return;
        }

        if (!printerJob.showPrintDialog(this)) {
            return;
        }

        Label titol = new Label("ICFact");
        titol.setStyle("""
                -fx-font-size: 28px;
                -fx-font-weight: bold;
                """);

        Label subtitol = new Label("Clau de recuperació");
        subtitol.setStyle("""
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                """);

        Label empresa = new Label(
                "Empresa: " + nomEmpresa
        );

        empresa.setStyle("""
                -fx-font-size: 15px;
                """);

        Label clau = new Label(clauRecuperacio);

        clau.setStyle("""
                -fx-font-size: 24px;
                -fx-font-weight: bold;
                """);

        Label avis = new Label(
                "Guarda aquest document en un lloc segur."
        );

        avis.setStyle("""
                -fx-font-size: 13px;
                """);

        VBox pagina = new VBox(
                20,
                titol,
                subtitol,
                empresa,
                clau,
                avis
        );

        pagina.setPadding(new Insets(50));

        PageLayout pageLayout = printerJob.getPrinter()
                .getDefaultPageLayout();

        boolean correcta = printerJob.printPage(
                pageLayout,
                pagina
        );

        if (correcta) {
            printerJob.endJob();
        }
    }
}