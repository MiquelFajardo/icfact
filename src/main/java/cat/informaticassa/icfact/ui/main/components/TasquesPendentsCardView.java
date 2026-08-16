package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import cat.informaticassa.icfact.ui.components.dialogs.TascaDialog;
import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class TasquesPendentsCardView extends VBox {
    private final TascaRepository tascaRepository = new TascaRepository();
    private static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TasquesPendentsCardView() {
        setSpacing(8);
        setPadding(new Insets(4, 0, 4, 0));
        carregar();
    }

    public void carregar() {
        getChildren().clear();
        var tasques = tascaRepository.buscarPendents();
        if (tasques.isEmpty()) {
            crearSenseTasques();
            return;
        }
        for (Tasca tasca : tasques) {
            getChildren().add(crearTasca(tasca));
        }
    }

    private void crearSenseTasques() {
        Label icona = new Label("✓");
        icona.setStyle("""
                -fx-background-color: #E8F8F0;
                -fx-background-radius: 25;
                -fx-text-fill: #22C55E;
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-alignment: center;
                """);
        icona.setMinSize(50, 50);
        icona.setPrefSize(50, 50);
        VBox text = getText();
        HBox fila = new HBox(16, icona, text);
        fila.setAlignment(Pos.CENTER_LEFT);
        getChildren().add(fila);
    }

    private static VBox getText() {
        Label titol = new Label("No hi ha tasques pendents.");
        titol.setStyle("""
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-text-fill: #334155;
                """);

        Label subtitol = new Label("Enhorabona! Tot al dia.");
        subtitol.setStyle("""
                -fx-font-size: 13px;
                -fx-text-fill: #94a3b8;
                """);

        VBox text = new VBox(4, titol, subtitol);
        text.setAlignment(Pos.CENTER_LEFT);
        return text;
    }

    private HBox crearTasca(Tasca tasca) {

        Label data = new Label();

        if (tasca.getDataLimit() != null) {
            data.setText(FORMAT_DATA.format(tasca.getDataLimit()));
        } else {
            data.setText("Sense data");
        }

        data.setMinWidth(100);
        data.setStyle("""
                -fx-font-size: 13px;
                -fx-font-weight: bold;
                -fx-text-fill: #64748b;
                """);

        Label titol = new Label(tasca.getTitol());
        titol.setStyle("""
                -fx-font-size: 14px;
                -fx-text-fill: #1e293b;
                """);

        HBox fila = new HBox(20, data, titol);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPadding(new Insets(11, 12, 11, 12));
        fila.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(titol, Priority.ALWAYS);

        fila.setStyle("""
                -fx-background-color: #f8fafc;
                -fx-background-radius: 8;
                -fx-border-color: #e2e8f0;
                -fx-border-radius: 8;
                -fx-cursor: hand;
                """);

        fila.setOnMouseEntered(e -> fila.setStyle("""
                -fx-background-color: #f1f5f9;
                -fx-background-radius: 8;
                -fx-border-color: #cbd5e1;
                -fx-border-radius: 8;
                -fx-cursor: hand;
                """));

        fila.setOnMouseExited(e -> fila.setStyle("""
                -fx-background-color: #f8fafc;
                -fx-background-radius: 8;
                -fx-border-color: #e2e8f0;
                -fx-border-radius: 8;
                -fx-cursor: hand;
                """));

        fila.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY &&
                    e.getClickCount() == 2) {
                editar(tasca);
            }
        });

        ContextMenu contextMenu = new ContextMenu();

        MenuItem editar = new MenuItem("Editar");
        MenuItem marcarFeta = new MenuItem("Marcar com a feta");

        contextMenu.getItems().addAll(editar, marcarFeta);

        editar.setOnAction(e -> editar(tasca));

        marcarFeta.setOnAction(e -> marcarComAFeta(tasca));

        fila.setOnContextMenuRequested(e -> contextMenu.show(
                fila,
                e.getScreenX(),
                e.getScreenY()
        ));

        return fila;
    }

    private void editar(Tasca tasca) {
        Stage stage = (Stage) getScene().getWindow();

        TascaDialog dialog = new TascaDialog(tasca);
        dialog.initOwner(stage);
        dialog.showAndWait();

        if (dialog.isDesadaCorrectament()) {
            carregar();
        }
    }

    private void marcarComAFeta(Tasca tasca) {
        boolean confirmar = Alerta.confirmar("Marcar tasca com a feta","Vols marcar la tasca com a feta?");
        if (!confirmar) {
            return;
        }
        tasca.setFeta(true);
        tascaRepository.actualitzar(tasca);
        carregar();
    }
}