package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.BuildInfo;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TopBar extends HBox {
    private final Button botoSortir = new Button("Sortir");

    public TopBar(Empresa empresa) {
        Color color = Color.web(empresa.getColor());
        Label empresaLabel = new Label("Empresa: " + empresa.getNom());

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(15);
        setPadding(new Insets(10, 20, 10, 20));
        setPrefHeight(60);
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Tema.BORDER, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));
        ImageView logo = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/logo.png"))));
        logo.setFitWidth(36);
        logo.setFitHeight(36);
        logo.setPreserveRatio(true);
        Label nomPrograma = new Label(BuildInfo.getNomAplicacio());
        nomPrograma.setFont(Tema.TITOL);
        Region espai = new Region();
        HBox.setHgrow(espai, Priority.ALWAYS);
        empresaLabel.setFont(Tema.SUBTITOL);
        crearBotoSortir();
        getChildren().addAll(logo, nomPrograma, espai, empresaLabel, botoSortir);
    }

    public void crearBotoSortir() {
        ImageView iconaSortir = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/tancar-sessio.png"))));
        iconaSortir.setFitWidth(30);
        iconaSortir.setFitHeight(30);
        iconaSortir.setPreserveRatio(true);
        botoSortir.setGraphic(iconaSortir);
        botoSortir.setPrefSize(42, 42);
        botoSortir.setText(null);
        botoSortir.setFocusTraversable(false);
        botoSortir.setTooltip(new Tooltip("Sortir"));
        aplicarEstilBotoSortir("rgba(255,255,255,0.20)");
        botoSortir.setOnMouseEntered(e -> aplicarEstilBotoSortir("rgba(255,255,255,0.35)"));
        botoSortir.setOnMouseExited(e -> aplicarEstilBotoSortir("rgba(255,255,255,0.20)"));
        botoSortir.setOnAction(e -> sortir());
    }

    private void aplicarEstilBotoSortir(String color) {
        botoSortir.setStyle("""
        -fx-background-color: %s;
        -fx-background-radius: 21;
        -fx-cursor: hand;
        """.formatted(color));
    }

    private void sortir() {
        boolean confirmar = Alerta.confirmar(getScene().getWindow(),"Sortir d'ICFact","Estàs segur que vols sortir de l'aplicació?");
        if (confirmar) {
            Platform.exit();
        }
    }
}