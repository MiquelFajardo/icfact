package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class MetricCard extends HBox {

    public MetricCard(
            String titol,
            String valor,
            String subtitol,
            String icona,
            String colorFonsIcona,
            String colorIcona) {

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(16);
        setPadding(new Insets(18));
        setMaxWidth(Double.MAX_VALUE);
        setPrefHeight(105);

        setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 14;
                -fx-border-color: #DCE3EC;
                -fx-border-width: 1;
                -fx-border-radius: 14;
                """);

        HBox contenidorIcona = new HBox();
        contenidorIcona.setAlignment(Pos.CENTER);
        contenidorIcona.setMinSize(54, 54);
        contenidorIcona.setPrefSize(54, 54);
        contenidorIcona.setMaxSize(54, 54);

        contenidorIcona.setStyle("""
                -fx-background-color: %s;
                -fx-background-radius: 27;
                """.formatted(colorFonsIcona));

        ImageView image = new ImageView(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("/icons/" + icona)
                        )
                )
        );

        image.setFitWidth(30);
        image.setFitHeight(30);
        image.setPreserveRatio(true);

        contenidorIcona.getChildren().add(image);

        Label lblTitol = new Label(titol);
        lblTitol.setFont(Tema.ETIQUETA);
        lblTitol.setStyle("""
                -fx-text-fill: #475569;
                """);

        Label lblValor = new Label(valor);
        lblValor.setStyle("""
                -fx-font-family: "Inter";
                -fx-font-size: 28px;
                -fx-font-weight: bold;
                -fx-text-fill: %s;
                """.formatted(colorIcona));

        Label lblSubtitol = new Label(subtitol);
        lblSubtitol.setStyle("""
                -fx-font-family: "Inter";
                -fx-font-size: 12px;
                -fx-text-fill: #94A3B8;
                """);

        VBox textos = new VBox(1, lblTitol, lblValor, lblSubtitol);
        textos.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(contenidorIcona, textos);

        setOnMouseEntered(e -> setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 14;
                -fx-border-color: #BFDBFE;
                -fx-border-width: 1;
                -fx-border-radius: 14;
                -fx-effect: dropshadow(gaussian, rgba(37,99,235,0.10), 10, 0, 0, 2);
                """));

        setOnMouseExited(e -> setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 14;
                -fx-border-color: #DCE3EC;
                -fx-border-width: 1;
                -fx-border-radius: 14;
                """));
    }
}