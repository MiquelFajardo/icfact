package cat.informaticassa.icfact.ui.main.components;

import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import cat.informaticassa.icfact.ui.components.dialogs.TascaDialog;
import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TasquesPendentsCardView extends VBox {
    private final TascaRepository repository = new TascaRepository();
    private static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TasquesPendentsCardView() {
        setSpacing(8);
        setPadding(new Insets(10));
        carregar();
    }

    public void carregar() {
        getChildren().clear();
        var tasques = repository.buscarPendents();
        if (tasques.isEmpty()) {
            getChildren().add( new Label("No hi ha tasques pendents."));
            return;
        }
        for (Tasca tasca : tasques) {
            getChildren().add(crearTasca(tasca));
        }
    }

    private CheckBox crearTasca(Tasca tasca) {
        String text = tasca.getTitol();
        if (tasca.getDataLimit() != null) {
            text += "   (" + FORMAT_DATA.format(tasca.getDataLimit()) +")";
        }
        CheckBox check = new CheckBox(text);
        check.setSelected(tasca.isFeta());
        check.setOnAction(e -> {
            if (!check.isSelected()) {
                return;
            }
            Stage stage = (Stage) getScene().getWindow();
            boolean confirmar = Alerta.confirmar(stage,"Marcar tasca com a feta","Vols marcar la tasca \"" + tasca.getTitol() + "\" com a feta?");
            if (!confirmar) {
                check.setSelected(false);
                return;
            }
            tasca.setFeta(true);
            tasca.setDataModificacio(LocalDateTime.now());
            repository.actualitzar(tasca);
            carregar();
        });

        check.setOnMouseClicked(e -> {
            if (e.getButton() == javafx.scene.input.MouseButton.PRIMARY && e.getClickCount() == 2) {
                editar(tasca);
            }
        });

        ContextMenu menu = new ContextMenu();
        MenuItem editar = new MenuItem("✏️ Editar");
        MenuItem feta = new MenuItem("✔ Marcar com a feta");
        editar.setOnAction(e -> editar(tasca));
        feta.setOnAction(e -> marcarComFeta(tasca));
        menu.getItems().addAll(editar, feta);
        check.setContextMenu(menu);
        return check;
    }

    private void editar(Tasca tasca) {
        Stage stage = (Stage) getScene().getWindow();
        TascaDialog dialog = new TascaDialog(tasca);
        dialog.initOwner(stage);
        dialog.showAndWait();
        if (dialog.isDesadaCorrectament()) {
            carregar();
        }
    }

    private void marcarComFeta(Tasca tasca) {
        Stage stage = (Stage) getScene().getWindow();
        boolean confirmar = Alerta.confirmar(stage,"Marcar tasca com a feta","Vols marcar la tasca \"" + tasca.getTitol() + "\" com a feta?");
        if (!confirmar) {
            return;
        }
        tasca.setFeta(true);
        tasca.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(tasca);
        carregar();
    }
}