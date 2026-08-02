package cat.informaticassa.icfact.ui.components.dialogs;

import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.BotoSecundari;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.Alerta;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import cat.informaticassa.icfact.ui.util.dirty.RespostaDirty;
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
    private final DirtyTracker dirtyTracker = new DirtyTracker();

    protected DialogBase(String titol) {
        this(titol, 600, 350);
    }

    protected DialogBase(String titol, double amplada, double alcada) {
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
        setWidth(amplada);
        setHeight(alcada);
        setMinWidth(amplada);
        setMinHeight(alcada);
        centerOnScreen();
        botoCancelar.setOnAction(e -> tancar());
        setOnCloseRequest(e -> {
            if (!dirtyTracker.estaModificat()) {
                return;
            }
            e.consume();
            tancar();
        });
    }

    private void tancar() {
        if (!dirtyTracker.estaModificat()) {
            close();
            return;
        }
        RespostaDirty resposta = Alerta.confirmarCanvis(this);
        switch (resposta) {
            case DESCARTAR -> close();
            case CANCELAR -> {}
            case DESAR -> botoGuardar.fire();
        }
    }
}