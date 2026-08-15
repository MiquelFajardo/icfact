package cat.informaticassa.icfact.ui.components.client;

import cat.informaticassa.icfact.ui.components.forms.FormGrid;
import cat.informaticassa.icfact.ui.components.geografia.adreca.AdrecaPane;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class ClientPane extends FormGrid {
    private final TextField txtNom = new TextField();
    private final TextField txtNomComercial = new TextField();
    private final TextField txtNif = new TextField();
    private final TextField txtTelefon = new TextField();
    private final TextField txtMobil = new TextField();
    private final TextField txtEmail = new TextField();
    private final TextField txtWeb = new TextField();
    private final AdrecaPane adrecaPane = new AdrecaPane();
    private final TextArea txtObservacions = new TextArea();
    private final CheckBox chkActiu = new CheckBox("Client actiu");


    public ClientPane() {
        afegirCamp("Nom", txtNom);
        afegirCamp("Nom comercial", txtNomComercial);
        afegirCamp("NIF", txtNif);
        afegirCamp("Telèfon", txtTelefon);
        afegirCamp("Mòbil", txtMobil);
        afegirCamp("Email", txtEmail);
        afegirCamp("Web", txtWeb);
        afegirCamp("Adreça", adrecaPane);
        txtObservacions.setPrefRowCount(4);
        afegirCamp("Observacions", txtObservacions);
        chkActiu.setSelected(true);
        afegirCamp("", chkActiu);
        chkActiu.setVisible(false);
        chkActiu.setManaged(false);
    }

    public void mostrarCampActiu(boolean mostrar) {
        chkActiu.setVisible(mostrar);
        chkActiu.setManaged(mostrar);
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(
                tracker,
                txtNom,
                txtNomComercial,
                txtNif,
                txtTelefon,
                txtMobil,
                txtEmail,
                txtWeb,
                txtObservacions,
                chkActiu
        );
        adrecaPane.registrarDirty(tracker);
    }
}