package cat.informaticassa.icfact.ui.main.pagines.client.form;

import cat.informaticassa.icfact.ui.main.pagines.client.altaClient.ClientAdrecaCard;
import cat.informaticassa.icfact.ui.main.pagines.client.altaClient.ClientButtons;
import cat.informaticassa.icfact.ui.main.pagines.client.altaClient.ClientDadesCard;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class ClientForm extends VBox {

    private final ClientDadesCard dadesCard = new ClientDadesCard();
    private final ClientAdrecaCard adrecaCard = new ClientAdrecaCard();
    private final ClientButtons buttons = new ClientButtons();

    public ClientForm() {

        setPadding(new Insets(20));
        setSpacing(20);

        getChildren().addAll(
                dadesCard,
                adrecaCard,
                buttons
        );
    }
}