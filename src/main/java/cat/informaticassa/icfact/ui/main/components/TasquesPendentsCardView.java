package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TasquesPendentsCardView extends VBox {

    private final TascaRepository repository = new TascaRepository();

    public TasquesPendentsCardView() {
        setSpacing(8);
        setPadding(new Insets(10));
        carregar();
    }

    private void carregar() {
        getChildren().clear();
        for (Tasca tasca : repository.buscarPendents()) {
            CheckBox check = new CheckBox(tasca.getTitol());
            getChildren().add(check);
        }

        if (getChildren().isEmpty()) {
            Label label = new Label("No hi ha tasques pendents.");
            getChildren().add(label);
        }
    }
}