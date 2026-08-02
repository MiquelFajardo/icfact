package cat.informaticassa.icfact.ui.main.pagines.client.altaClient;

import cat.informaticassa.icfact.ui.main.pagines.client.form.ClientForm;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

@Getter
public class ClientPage extends BorderPane {

    private final ClientForm formulari = new ClientForm();

    public ClientPage() {

        setPadding(new Insets(20));

        setCenter(formulari);

        new ClientController(this);
    }
}