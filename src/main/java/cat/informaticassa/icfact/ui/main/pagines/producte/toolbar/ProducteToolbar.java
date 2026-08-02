package cat.informaticassa.icfact.ui.main.pagines.producte.toolbar;

import cat.informaticassa.icfact.ui.components.BotoPrimari;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class ProducteToolbar extends VBox {
    private final Label titol = new Label("Productes");
    private final TextField txtBuscar = new TextField();
    private final CheckBox chkActius = new CheckBox("Actius");
    private final CheckBox chkInactius = new CheckBox("Inactius");
    private final BotoPrimari botoNou = new BotoPrimari("Nou producte");

    public ProducteToolbar() {
        setSpacing(15);
        setPadding(new Insets(20));
        titol.setStyle("""
                -fx-font-size:26px;
                -fx-font-weight:bold;
                """);
        chkActius.setSelected(true);
        txtBuscar.setPromptText("Buscar per nom, referència o descripció...");
        Region espai = new Region();
        HBox.setHgrow(espai, Priority.ALWAYS);
        HBox primeraFila = new HBox(
                titol,
                espai,
                botoNou
        );
        primeraFila.setAlignment(Pos.CENTER_LEFT);
        Region espai2 = new Region();
        HBox.setHgrow(espai2, Priority.ALWAYS);
        HBox segonaFila = new HBox(
                txtBuscar,
                espai2,
                chkActius,
                chkInactius
        );
        segonaFila.setSpacing(20);
        segonaFila.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(txtBuscar, Priority.ALWAYS);
        getChildren().addAll(
                primeraFila,
                segonaFila
        );
    }
}