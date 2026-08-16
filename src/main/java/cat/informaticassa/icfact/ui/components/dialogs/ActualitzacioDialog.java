package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.actualitzacio.model.InformacioActualitzacio;
import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ActualitzacioDialog extends Stage {
    public enum Resultat {
        ACTUALITZAR,
        MES_ENDAVANT,
        VEURE_NOTES
    }
    private Resultat resultat;
    public ActualitzacioDialog(Window owner, InformacioActualitzacio informacio) {
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Actualització disponible");
        setResizable(false);
        setOnCloseRequest(e -> {
            if (!confirmarSortida()) {
                e.consume();
            }
        });
        construir(informacio);
        sizeToScene();
        centerOnScreen();
    }

    private void construir(InformacioActualitzacio informacio) {
        VBox principal = new VBox(18);
        principal.setAlignment(Pos.CENTER);
        principal.setPadding(new Insets(30, 35, 28, 35));
        principal.setPrefWidth(500);
        principal.setBackground(new Background(
                new BackgroundFill(
                        Color.web("#071222"),
                        new CornerRadii(16),
                        Insets.EMPTY
                )
        ));

        Label icona = new Label("↻");
        icona.setMinSize(64, 64);
        icona.setPrefSize(64, 64);
        icona.setAlignment(Pos.CENTER);
        icona.setFont(Font.font("System", 32));
        icona.setTextFill(Color.WHITE);
        icona.setStyle("""
                -fx-background-color: #2563EB;
                -fx-background-radius: 32px;
                -fx-font-weight: bold;
                """);

        Label titol = new Label("Nova versió disponible");
        titol.setFont(Font.font("System", 24));
        titol.setTextFill(Color.WHITE);
        titol.setStyle("""
                -fx-font-weight: bold;
                """);

        Label text = new Label("Hi ha una nova versió d'ICFact disponible.");
        text.setFont(Font.font("System", 15));
        text.setTextFill(Color.web("#9fb0c8"));

        Label versio = new Label("Versió " + informacio.getUltimaVersio());
        versio.setFont(Font.font("System", 20));
        versio.setTextFill(Color.web("#3B82F6"));
        versio.setStyle("""
                -fx-font-weight: bold;
                """);

        Region separador = new Region();
        separador.setPrefHeight(1);
        separador.setMaxWidth(Double.MAX_VALUE);

        separador.setBackground(new Background(new BackgroundFill(Color.web("#26354d"), CornerRadii.EMPTY, Insets.EMPTY)));
        Button veureNotes = new Button("Veure notes");
        Button mesEndavant = new Button("Més endavant");
        Button actualitzar = new Button("Actualitzar");
        estilBotons(veureNotesStyle(), veureNotes);

        estilBotons("""
                -fx-background-color: #26354d;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 10px;
                -fx-padding: 11px 20px;
                -fx-cursor: hand;
                """,
                mesEndavant
        );

        estilBotons("""
                -fx-background-color: linear-gradient(to right, #1687ff, #3932ff);
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 10px;
                -fx-padding: 11px 24px;
                -fx-cursor: hand;
                """,
                actualitzar
        );

        mesEndavant.setOnAction(e -> confirmarSortida());

        actualitzar.setOnAction(e -> {
            resultat = Resultat.ACTUALITZAR;
            close();
        });

        veureNotes.setOnAction(e -> {
            resultat = Resultat.VEURE_NOTES;
            close();
        });

        HBox botons = new HBox(10);
        botons.setAlignment(Pos.CENTER);
        botons.getChildren().addAll(
                veureNotes,
                mesEndavant,
                actualitzar
        );

        principal.getChildren().addAll(
                icona,
                titol,
                text,
                versio,
                separador,
                botons
        );

        Scene scene = new Scene(principal);
        scene.setFill(Color.TRANSPARENT);

        setScene(scene);
    }


    private boolean confirmarSortida() {
        boolean confirmar = Alerta.confirmar(this, "No actualitzar ara","Segur que no vols actualitzar ICFact?");
        if (confirmar) {
            resultat = Resultat.MES_ENDAVANT;
            close();
            return true;
        }
        return false;
    }

    private void estilBotons(String estil, Button boto) {
        boto.setStyle(estil);
        boto.setMinHeight(42);
    }

    private String veureNotesStyle() {
        return """
                -fx-background-color: transparent;
                -fx-border-color: #3B82F6;
                -fx-border-width: 1px;
                -fx-text-fill: #60A5FA;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 10px;
                -fx-border-radius: 10px;
                -fx-padding: 10px 18px;
                -fx-cursor: hand;
                """;
    }

    public Resultat mostrar() {
        showAndWait();
        return resultat;
    }
}