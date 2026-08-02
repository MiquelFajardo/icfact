package cat.informaticassa.icfact.ui.main.pagines.client.fitxa;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.ui.components.BotoPerill;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import lombok.Getter;

@Getter
public class ClientFitxaHeader extends HBox {

    private final Label lblTornar = new Label("← Clients");
    private final Label lblNom = new Label();
    private final Label lblEstat = new Label();

    private final BotoPrimari botoEditar = new BotoPrimari("Editar");
    private final BotoPrimari botoGuardar = new BotoPrimari("Guardar");
    private final BotoPerill botoCancelar = new BotoPerill("Cancel·lar");

    public ClientFitxaHeader() {

        setPadding(new Insets(20));
        setSpacing(15);

        lblNom.setStyle("-fx-font-size:22px;-fx-font-weight:bold;");

        lblEstat.setStyle("""
                -fx-font-weight: bold;
                """);

        Region espai = new Region();
        HBox.setHgrow(espai, Priority.ALWAYS);

        getChildren().addAll(
                lblTornar,
                lblNom,
                espai,
                lblEstat,
                botoEditar,
                botoGuardar,
                botoCancelar
        );

        mostrarModeConsulta();
    }

    public void mostrar(Client client) {

        lblNom.setText(client.getNom());

        if (client.isActiu()) {
            lblEstat.setText("🟢 Actiu");
        } else {
            lblEstat.setText("🔴 Inactiu");
        }
    }

    public void mostrarModeConsulta() {

        botoEditar.setVisible(true);
        botoEditar.setManaged(true);

        botoGuardar.setVisible(false);
        botoGuardar.setManaged(false);

        botoCancelar.setVisible(false);
        botoCancelar.setManaged(false);
    }

    public void mostrarModeEdicio() {

        botoEditar.setVisible(false);
        botoEditar.setManaged(false);

        botoGuardar.setVisible(true);
        botoGuardar.setManaged(true);

        botoCancelar.setVisible(true);
        botoCancelar.setManaged(true);
    }
}