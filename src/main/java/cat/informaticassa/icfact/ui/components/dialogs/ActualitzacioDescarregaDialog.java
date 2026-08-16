package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.actualitzacio.service.ActualitzacioDownloader;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.nio.file.Path;

public class ActualitzacioDescarregaDialog extends Stage {
    private final ProgressBar progressBar = new ProgressBar(0);
    private final Label estat = new Label("Preparant la descàrrega...");
    private final Label percentatge = new Label("0 %");
    private final Label mida = new Label("");
    private final Button tancar = new Button("Tancar");

    public ActualitzacioDescarregaDialog(Window owner) {
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Actualitzant ICFact");
        setResizable(false);
        construir();
        setOnCloseRequest(e -> {
            if (!tancar.isDisable()) {
                return;
            }
            e.consume();
        });
    }

    private void construir() {
        VBox principal = new VBox(16);
        principal.setAlignment(Pos.CENTER);
        principal.setPadding(new Insets(30, 35, 30, 35));
        principal.setPrefWidth(500);

        principal.setStyle("""
                -fx-background-color: #071222;
                -fx-background-radius: 16px;
                """);

        Label icona = new Label("↓");
        icona.setMinSize(64, 64);
        icona.setPrefSize(64, 64);
        icona.setAlignment(Pos.CENTER);
        icona.setFont(Font.font("System", 30));
        icona.setTextFill(Color.WHITE);

        icona.setStyle("""
                -fx-background-color: #2563EB;
                -fx-background-radius: 32px;
                -fx-font-weight: bold;
                """);

        Label titol = new Label("Actualitzant ICFact");
        titol.setFont(Font.font("System", 24));
        titol.setTextFill(Color.WHITE);
        titol.setStyle("""
                -fx-font-weight: bold;
                """);

        Label avis = new Label("Fes una còpia de seguretat de les dades abans d'actualitzar.");

        avis.setWrapText(true);
        avis.setAlignment(Pos.CENTER);
        avis.setTextFill(Color.web("#FBBF24"));
        avis.setFont(Font.font("System", 14));
        avis.setStyle("""
                -fx-font-weight: bold;
                """);

        estat.setTextFill(Color.web("#9FB0C8"));
        estat.setFont(Font.font("System", 14));

        progressBar.setPrefWidth(420);
        progressBar.setPrefHeight(18);

        percentatge.setTextFill(Color.WHITE);
        percentatge.setFont(Font.font("System", 18));
        percentatge.setStyle("""
                -fx-font-weight: bold;
                """);

        mida.setTextFill(Color.web("#9FB0C8"));
        mida.setFont(Font.font("System", 13));

        tancar.setText("Tancar");
        tancar.setMinWidth(110);
        tancar.setMinHeight(40);
        tancar.setDisable(true);

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
                avis,
                estat,
                progressBar,
                percentatge,
                mida,
                tancar
        );

        setScene(new Scene(principal));
        sizeToScene();
        centerOnScreen();
    }

    public void iniciarDescarga(String url, Path desti) {
        String nomFitxer = desti.getFileName().toString();
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                ActualitzacioDownloader.descarregar(url, desti, (bytesDescarregats, totalBytes) -> {
                            if (totalBytes > 0) {
                                updateProgress(bytesDescarregats, totalBytes);
                                updateMessage(bytesDescarregats + ";" + totalBytes);
                            }
                        }
                );
                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());
        task.messageProperty().addListener((obs, anterior, nou) -> {
            if (nou == null || nou.isBlank()) {
                return;
            }
            String[] dades = nou.split(";");
            if (dades.length != 2) {
                return;
            }
            try {
                long descarregats = Long.parseLong(dades[0]);
                long total = Long.parseLong(dades[1]);
                double percent = ((double) descarregats / total) * 100;
                percentatge.setText(String.format("%.0f %%", percent));
                mida.setText(formatBytes(descarregats) + " / " + formatBytes(total));
            } catch (NumberFormatException ignored) {
                // Informació visual
            }
        });

        task.setOnSucceeded(e -> {
            progressBar.progressProperty().unbind();
            progressBar.setProgress(1);
            percentatge.setText("100 %");
            estat.setText("Descàrrega completada.");
            mida.setText("Fitxer: " + nomFitxer);
            tancar.setDisable(false);
        });

        task.setOnFailed(e -> {
            progressBar.progressProperty().unbind();
            estat.setText("No s'ha pogut descarregar l'actualització.");
            percentatge.setText("");
            Throwable error = task.getException();
            if (error != null) {
                mida.setText(error.getMessage());
            }
            tancar.setDisable(false);
        });
        Thread thread = new Thread( task,"icfact-actualitzacio");
        thread.setDaemon(true);
        thread.start();
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        if (bytes < 1024 * 1024) {
            return String.format(
                    "%.1f KB",
                    bytes / 1024.0
            );
        }
        if (bytes < 1024L * 1024L * 1024L) {
            return String.format(
                    "%.1f MB",
                    bytes / (1024.0 * 1024.0)
            );
        }

        return String.format(
                "%.1f GB",
                bytes / (1024.0 * 1024.0 * 1024.0)
        );
    }
}