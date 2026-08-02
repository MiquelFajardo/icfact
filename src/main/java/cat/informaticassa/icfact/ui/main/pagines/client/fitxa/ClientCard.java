package cat.informaticassa.icfact.ui.main.pagines.client.fitxa;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.ui.components.Card;
import cat.informaticassa.icfact.ui.components.Icones;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class ClientCard extends Card {

    private final TextField txtNom = new TextField();
    private final TextField txtNif = new TextField();
    private final TextField txtTelefon = new TextField();
    private final TextField txtEmail = new TextField();
    private final TextField txtWeb = new TextField();
    private final TextField txtAdreca = new TextField();

    private final Label lblEstat = new Label();

    public ClientCard() {

        super("Dades del client");

        VBox contingut = new VBox(18);
        contingut.setPadding(new Insets(5, 0, 0, 0));

        contingut.getChildren().addAll(
                crearCamp("👤  Nom", txtNom),
                crearCamp("🆔  NIF", txtNif),
                crearCamp("📱  Telèfon", txtTelefon),
                crearCamp("📨  Email", txtEmail),
                crearCamp("🌐  Web", txtWeb),
                crearCamp("🏡  Adreça", txtAdreca),
                lblEstat
        );

        afegir(contingut);

        mostrarModeConsulta();
    }
    private VBox crearCamp(String titol, TextField camp) {

        Label lbl = new Label(titol);
        lbl.setStyle("""
            -fx-font-weight: bold;
            -fx-font-size: 13px;
            -fx-text-fill: #374151;
            """);

        camp.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(camp, Priority.NEVER);

        VBox box = new VBox(6);

        box.getChildren().addAll(
                lbl,
                camp
        );

        return box;
    }

    public void mostrar(Client client) {

        txtNom.setText(valor(client.getNom()));
        txtNif.setText(valor(client.getNif()));
        txtTelefon.setText(valor(client.getTelefon()));
        txtEmail.setText(valor(client.getEmail()));
        txtWeb.setText(valor(client.getWeb()));

        if (client.getAdreca() != null) {
            txtAdreca.setText(valor(client.getAdreca().getAdrecaCompleta()));
        } else {
            txtAdreca.clear();
        }

        lblEstat.setText(
                client.isActiu()
                        ? "🟢 Client actiu"
                        : "🔴 Client inactiu"
        );
    }

    public void mostrarModeConsulta() {

        configurarConsulta(txtNom);
        configurarConsulta(txtNif);
        configurarConsulta(txtTelefon);
        configurarConsulta(txtEmail);
        configurarConsulta(txtWeb);
        configurarConsulta(txtAdreca);
    }

    public void mostrarModeEdicio() {

        configurarEdicio(txtNom);
        configurarEdicio(txtNif);
        configurarEdicio(txtTelefon);
        configurarEdicio(txtEmail);
        configurarEdicio(txtWeb);
        configurarEdicio(txtAdreca);
    }

    private void configurarConsulta(TextField camp) {

        camp.setEditable(false);
        camp.setFocusTraversable(false);

        camp.setStyle("""
                -fx-background-color: transparent;
                -fx-border-color: transparent;
                -fx-padding: 6 0 6 0;
                -fx-font-size: 14px;
                """);
    }

    private void configurarEdicio(TextField camp) {

        camp.setEditable(true);
        camp.setFocusTraversable(true);

        camp.setStyle("");
    }

    private String valor(String text) {
        return text == null ? "" : text;
    }
}