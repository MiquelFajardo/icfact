package cat.informaticassa.icfact.ui.main.components.geografia;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class AdrecaPane extends GridPane {

    private final TextField txtCarrer = new TextField();
    private final TextField txtNumero = new TextField();
    private final TextField txtPis = new TextField();
    private final TextField txtPorta = new TextField();
    private final TextField txtCodiPostal = new TextField();

    private final ComboBox<Pais> cmbPais = new ComboBox<>();
    private final ComboBox<Provincia> cmbProvincia = new ComboBox<>();
    private final ComboBox<Poblacio> cmbPoblacio = new ComboBox<>();

    private final Button botoNouPais = new Button("+");
    private final Button botoNovaProvincia = new Button("+");
    private final Button botoNovaPoblacio = new Button("+");

    public AdrecaPane() {
        setHgap(15);
        setVgap(15);
        int fila = 0;

        add(new FormLabel("Adreça"), 0, fila);
        add(txtCarrer, 1, fila++);

        add(new FormLabel("Número"), 0, fila);
        add(txtNumero, 1, fila++);

        add(new FormLabel("Pis"), 0, fila);
        add(txtPis, 1, fila++);

        add(new FormLabel("Porta"), 0, fila);
        add(txtPorta, 1, fila++);

        add(new FormLabel("Codi postal"), 0, fila);
        add(txtCodiPostal, 1, fila++);

        add(new FormLabel("País"), 0, fila);

        HBox filaPais = new HBox(10);
        HBox.setHgrow(cmbPais, Priority.ALWAYS);
        filaPais.getChildren().addAll(cmbPais, botoNouPais);

        add(filaPais, 1, fila++);

        add(new FormLabel("Província"), 0, fila);

        HBox filaProvincia = new HBox(10);
        HBox.setHgrow(cmbProvincia, Priority.ALWAYS);
        filaProvincia.getChildren().addAll(cmbProvincia, botoNovaProvincia);

        add(filaProvincia, 1, fila++);

        add(new FormLabel("Població"), 0, fila);

        HBox filaPoblacio = new HBox(10);
        HBox.setHgrow(cmbPoblacio, Priority.ALWAYS);
        filaPoblacio.getChildren().addAll(cmbPoblacio, botoNovaPoblacio);

        add(filaPoblacio, 1, fila);
        configurarAmplades();
    }

    private void configurarAmplades() {
        txtCarrer.setMaxWidth(Double.MAX_VALUE);
        txtNumero.setMaxWidth(Double.MAX_VALUE);
        txtPis.setMaxWidth(Double.MAX_VALUE);
        txtPorta.setMaxWidth(Double.MAX_VALUE);
        txtCodiPostal.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(txtCarrer, Priority.ALWAYS);
        GridPane.setHgrow(txtNumero, Priority.ALWAYS);
        GridPane.setHgrow(txtPis, Priority.ALWAYS);
        GridPane.setHgrow(txtPorta, Priority.ALWAYS);
        GridPane.setHgrow(txtCodiPostal, Priority.ALWAYS);
        cmbPais.setMaxWidth(Double.MAX_VALUE);
        cmbProvincia.setMaxWidth(Double.MAX_VALUE);
        cmbPoblacio.setMaxWidth(Double.MAX_VALUE);
    }

    public void mostrar(Adreca adreca) {
        System.out.println(">>> AdrecaPane.mostrar()");
        txtCarrer.setText(adreca.getCarrer());
        txtNumero.setText(adreca.getNumero());
        txtPis.setText(adreca.getPis());
        txtPorta.setText(adreca.getPorta());
        txtCodiPostal.setText(adreca.getCodiPostal());

        Poblacio poblacio = adreca.getPoblacio();
        Provincia provincia = poblacio.getProvincia();
        Pais pais = provincia.getPais();

        for (Pais p : cmbPais.getItems()) {
            System.out.println(p.getId() + " - " + p.getNom());
        }

        cmbPais.getItems().stream()
                .filter(p -> p.getId().equals(pais.getId()))
                .findFirst()
                .ifPresent(cmbPais::setValue);

        cmbProvincia.getItems().stream()
                .filter(p -> p.getId().equals(provincia.getId()))
                .findFirst()
                .ifPresent(cmbProvincia::setValue);

        cmbPoblacio.getItems().stream()
                .filter(p -> p.getId().equals(poblacio.getId()))
                .findFirst()
                .ifPresent(cmbPoblacio::setValue);
    }

    public void actualitzar(Adreca adreca) {
        adreca.setCarrer(txtCarrer.getText());
        adreca.setNumero(txtNumero.getText());
        adreca.setPis(txtPis.getText());
        adreca.setPorta(txtPorta.getText());
        adreca.setCodiPostal(txtCodiPostal.getText());
        adreca.setPoblacio(cmbPoblacio.getValue());
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(
                tracker,
                txtCarrer,
                txtNumero,
                txtPis,
                txtPorta,
                txtCodiPostal,
                cmbPais,
                cmbProvincia,
                cmbPoblacio
        );
    }
}