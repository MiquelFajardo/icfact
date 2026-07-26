package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InfoCard extends VBox {

    private final Label valor;

    public InfoCard(String titol, int valorInicial) {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(20));
        setPrefSize(170, 120);
        setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 12;
                -fx-border-color: #D8D8D8;
                -fx-border-radius: 12;
                """);

        Label lblTitol = new Label(titol);
        lblTitol.setFont(Tema.TEXT_NORMAL);

        valor = new Label(String.valueOf(valorInicial));
        valor.setFont(Tema.TITOL);

        getChildren().addAll(lblTitol, valor);
    }

    public void setValor(int nouValor) {
        valor.setText(String.valueOf(nouValor));
    }

    public int getValor() {
        return Integer.parseInt(valor.getText());
    }
}