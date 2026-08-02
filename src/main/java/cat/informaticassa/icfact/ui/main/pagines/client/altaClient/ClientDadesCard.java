package cat.informaticassa.icfact.ui.main.pagines.client.altaClient;

import cat.informaticassa.icfact.ui.components.Card;
import cat.informaticassa.icfact.ui.components.FormField;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class ClientDadesCard extends Card {

    private final FormField nom = new FormField("👤 Nom");
    private final FormField nif = new FormField("🆔 NIF");
    private final FormField telefon = new FormField("📞 Telèfon");
    private final FormField email = new FormField("📧 Email");
    private final FormField web = new FormField("🌐 Web");

    public ClientDadesCard() {

        super("Dades principals");

        nom.setPromptText("Nom del client");
        nif.setPromptText("NIF");
        telefon.setPromptText("Telèfon");
        email.setPromptText("Correu electrònic");
        web.setPromptText("https://");

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10));
        grid.setHgap(20);
        grid.setVgap(20);

        grid.add(nom, 0, 0);
        grid.add(nif, 1, 0);

        grid.add(telefon, 0, 1);
        grid.add(email, 1, 1);

        grid.add(web, 0, 2, 2, 1);

        GridPane.setHgrow(nom, Priority.ALWAYS);
        GridPane.setHgrow(nif, Priority.ALWAYS);
        GridPane.setHgrow(telefon, Priority.ALWAYS);
        GridPane.setHgrow(email, Priority.ALWAYS);
        GridPane.setHgrow(web, Priority.ALWAYS);

        afegir(grid);
    }
}