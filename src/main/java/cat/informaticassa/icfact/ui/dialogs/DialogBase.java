package cat.informaticassa.icfact.ui.dialogs;

import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.BotoSecundari;
import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public abstract class DialogBase extends Stage {
    protected final BotoPrimari botoGuardar = new BotoPrimari("💾 Desa");
    protected final BotoSecundari botoCancelar = new BotoSecundari("❌ Cancel·la");
    private final BorderPane root = new BorderPane();

    protected DialogBase(String titol) {
        initModality(Modality.APPLICATION_MODAL);
        setTitle(titol);
        root.setPadding(new Insets(20));

        HBox botons = new HBox(10);
        botons.setAlignment(Pos.CENTER_RIGHT);
        botons.getChildren().addAll(botoCancelar, botoGuardar);
        root.setBottom(botons);

        Scene scene = new Scene(root);
        scene.setFill(Tema.FONS);
        setScene(scene);

        setWidth(600);
        setHeight(350);
        setMinWidth(500);
        setMinHeight(250);
        centerOnScreen();

        botoCancelar.setOnAction(e -> close());
    }
}