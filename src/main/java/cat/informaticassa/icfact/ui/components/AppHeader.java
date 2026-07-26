package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.Objects;

public class AppHeader extends VBox {

    public AppHeader(String titol, String subtitol) {
        setAlignment(Pos.CENTER);
        setSpacing(6);
        ImageView logo = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/logo.png"))));
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        Label lblTitol = new Label(titol);
        lblTitol.setFont(Tema.TITOL);
        lblTitol.setTextFill(Tema.TEXT);
        Label lblSubtitol = new Label(subtitol);
        lblSubtitol.setFont(Tema.SUBTITOL);
        lblSubtitol.setTextFill(Tema.TEXT_SECUNDARI);
        lblSubtitol.setWrapText(true);
        lblSubtitol.setAlignment(Pos.CENTER);
        getChildren().addAll(logo,  lblTitol, lblSubtitol);
    }

    /**
     * Permet canviar la icona (més endavant amb Ikonli).
     */
    public void setIcona(String emoji) {
        if (!getChildren().isEmpty() && getChildren().getFirst() instanceof Label label) {
            label.setText(emoji);
        }
    }
}