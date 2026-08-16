package cat.informaticassa.icfact.ui.components.dialogs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;

public class NotesActualitzacioDialog extends Stage {
    public NotesActualitzacioDialog(Window owner, String versio, List<String> notes) {
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Notes de l'actualització");
        setResizable(false);

        VBox principal = new VBox(18);
        principal.setAlignment(Pos.TOP_CENTER);
        principal.setPadding(new Insets(30, 35, 28, 35));
        principal.setPrefWidth(500);

        principal.setStyle("""
                -fx-background-color: #071222;
                -fx-background-radius: 16px;
                """);

        Label icona = new Label("✓");
        icona.setMinSize(58, 58);
        icona.setPrefSize(58, 58);
        icona.setAlignment(Pos.CENTER);
        icona.setFont(Font.font("System", 28));
        icona.setTextFill(Color.WHITE);
        icona.setStyle("""
                -fx-background-color: #2563EB;
                -fx-background-radius: 29px;
                -fx-font-weight: bold;
                """);

        Label titol = new Label("Notes de l'actualització");
        titol.setFont(Font.font("System", 23));
        titol.setTextFill(Color.WHITE);
        titol.setStyle("""
                -fx-font-weight: bold;
                """);

        Label versioLabel = new Label("Versió " + versio);
        versioLabel.setFont(Font.font("System", 17));
        versioLabel.setTextFill(Color.web("#60A5FA"));
        versioLabel.setStyle("""
                -fx-font-weight: bold;
                """);

        VBox notesBox = new VBox(12);
        notesBox.setPadding(new Insets(5));

        if (notes != null && !notes.isEmpty()) {

            for (String nota : notes) {

                Label punt = new Label("•");
                punt.setFont(Font.font("System", 16));
                punt.setTextFill(Color.web("#3B82F6"));

                Label text = new Label(nota);
                text.setWrapText(true);
                text.setFont(Font.font("System", 14));
                text.setTextFill(Color.web("#D5DFEC"));

                VBox notaBox = new VBox();
                notaBox.setSpacing(2);

                notaBox.getChildren().add(
                        new javafx.scene.layout.HBox(8, punt, text)
                );

                notesBox.getChildren().add(notaBox);
            }

        } else {

            Label senseNotes = new Label(
                    "No hi ha notes disponibles per aquesta versió."
            );

            senseNotes.setFont(Font.font("System", 14));
            senseNotes.setTextFill(Color.web("#9FB0C8"));

            notesBox.getChildren().add(senseNotes);
        }

        ScrollPane scroll = new ScrollPane(notesBox);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(220);
        scroll.setMaxHeight(220);

        scroll.setStyle("""
                -fx-background-color: transparent;
                -fx-background: transparent;
                -fx-border-color: transparent;
                """);

        Button tancar = new Button("Tancar");

        tancar.setMinWidth(110);
        tancar.setMinHeight(40);

        tancar.setStyle("""
                -fx-background-color: #26354D;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 10px;
                -fx-padding: 10px 20px;
                -fx-cursor: hand;
                """);

        tancar.setOnAction(e -> close());

        principal.getChildren().addAll(
                icona,
                titol,
                versioLabel,
                scroll,
                tancar
        );

        setScene(new Scene(principal));
        sizeToScene();
        centerOnScreen();
    }

    public void mostrar() {
        showAndWait();
    }
}