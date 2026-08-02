package cat.informaticassa.icfact.ui.main.pagines.client.altaClient;

import cat.informaticassa.icfact.ui.components.BotoPerill;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.Card;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import lombok.Getter;

@Getter
public class ClientButtons extends Card {

    private final BotoPerill botoCancelar = new BotoPerill("Cancel·lar");
    private final BotoPrimari botoGuardar = new BotoPrimari("Guardar client");

    public ClientButtons() {

        HBox botons = new HBox(10);

        Region espai = new Region();
        HBox.setHgrow(espai, Priority.ALWAYS);

        botons.setAlignment(Pos.CENTER_RIGHT);

        botons.getChildren().addAll(
                espai,
                botoCancelar,
                botoGuardar
        );

        afegir(botons);
    }
}