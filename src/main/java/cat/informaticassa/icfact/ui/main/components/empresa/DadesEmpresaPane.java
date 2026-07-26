package cat.informaticassa.icfact.ui.main.components.empresa;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class DadesEmpresaPane extends GridPane {
    private final TextField txtNom = new TextField();
    private final TextField txtDescripcio = new TextField();
    private final TextField txtNif = new TextField();
    private final TextField txtTelefon = new TextField();
    private final TextField txtEmail = new TextField();
    private final TextField txtWeb = new TextField();
    private final TextField txtIban = new TextField();

    public DadesEmpresaPane() {
        setHgap(15);
        setVgap(15);
        setMaxWidth(Double.MAX_VALUE);
        int fila = 0;

        add(new FormLabel("Nom"), 0, fila);
        add(txtNom, 1, fila++);

        add(new FormLabel("Descripció"), 0, fila);
        add(txtDescripcio, 1, fila++);

        add(new FormLabel("NIF"), 0, fila);
        add(txtNif, 1, fila++);

        add(new FormLabel("Telèfon"), 0, fila);
        add(txtTelefon, 1, fila++);

        add(new FormLabel("Email"), 0, fila);
        add(txtEmail, 1, fila++);

        add(new FormLabel("Web"), 0, fila);
        add(txtWeb, 1, fila++);

        add(new FormLabel("IBAN"), 0, fila);
        add(txtIban, 1, fila);

        configurarAmplades();
    }

    private void configurarAmplades() {
        txtNom.setMaxWidth(Double.MAX_VALUE);
        txtDescripcio.setMaxWidth(Double.MAX_VALUE);
        txtNif.setMaxWidth(Double.MAX_VALUE);
        txtTelefon.setMaxWidth(Double.MAX_VALUE);
        txtEmail.setMaxWidth(Double.MAX_VALUE);
        txtWeb.setMaxWidth(Double.MAX_VALUE);
        txtIban.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(txtNom, Priority.ALWAYS);
        GridPane.setHgrow(txtDescripcio, Priority.ALWAYS);
        GridPane.setHgrow(txtNif, Priority.ALWAYS);
        GridPane.setHgrow(txtTelefon, Priority.ALWAYS);
        GridPane.setHgrow(txtEmail, Priority.ALWAYS);
        GridPane.setHgrow(txtWeb, Priority.ALWAYS);
        GridPane.setHgrow(txtIban, Priority.ALWAYS);
    }

    public void mostrar(Empresa empresa) {
        txtNom.setText(empresa.getNom());
        txtDescripcio.setText(empresa.getDescripcio());
        txtNif.setText(empresa.getNif());
        txtTelefon.setText(empresa.getTelefon());
        txtEmail.setText(empresa.getEmail());
        txtWeb.setText(empresa.getWeb());
        txtIban.setText(empresa.getIban());
    }

    public void actualitzar(Empresa empresa) {
        empresa.setNom(txtNom.getText());
        empresa.setDescripcio(txtDescripcio.getText());
        empresa.setNif(txtNif.getText());
        empresa.setTelefon(txtTelefon.getText());
        empresa.setEmail(txtEmail.getText());
        empresa.setWeb(txtWeb.getText());
        empresa.setIban(txtIban.getText());
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(
                tracker,
                txtNom,
                txtDescripcio,
                txtNif,
                txtTelefon,
                txtEmail,
                txtWeb,
                txtIban
        );
    }
}