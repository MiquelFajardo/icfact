package cat.informaticassa.icfact.ui.components.geografia.adreca;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.ui.main.components.FormLabel;
import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.ui.util.dirty.DirtyBindings;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

@Getter
public class AdrecaPane extends GridPane {
    private final AdrecaController controller;
    private final TextField txtCarrer = new TextField();
    private final TextField txtNumero = new TextField();
    private final TextField txtPis = new TextField();
    private final TextField txtPorta = new TextField();
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
        controller = new AdrecaController(this);
    }

    private void configurarAmplades() {
        txtCarrer.setMaxWidth(Double.MAX_VALUE);
        txtNumero.setMaxWidth(Double.MAX_VALUE);
        txtPis.setMaxWidth(Double.MAX_VALUE);
        txtPorta.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(txtCarrer, Priority.ALWAYS);
        GridPane.setHgrow(txtNumero, Priority.ALWAYS);
        GridPane.setHgrow(txtPis, Priority.ALWAYS);
        GridPane.setHgrow(txtPorta, Priority.ALWAYS);
        cmbPais.setMaxWidth(Double.MAX_VALUE);
        cmbProvincia.setMaxWidth(Double.MAX_VALUE);
        cmbPoblacio.setMaxWidth(Double.MAX_VALUE);
    }

    public void mostrar(Adreca adreca) {

        txtCarrer.setText(adreca.getCarrer());
        txtNumero.setText(adreca.getNumero());
        txtPis.setText(adreca.getPis());
        txtPorta.setText(adreca.getPorta());

        // Netejar seleccions
        cmbPais.setValue(null);

        cmbProvincia.getItems().clear();
        cmbProvincia.setValue(null);
        cmbProvincia.setDisable(true);

        cmbPoblacio.getItems().clear();
        cmbPoblacio.setValue(null);
        cmbPoblacio.setDisable(true);

        // -------------------------
        // PAÍS
        // -------------------------

        if (adreca.getPais() == null) {
            return;
        }

        Long paisId = adreca.getPais().getId();

        cmbPais.getItems().stream()
                .filter(pais -> pais.getId().equals(paisId))
                .findFirst()
                .ifPresent(pais -> {

                    cmbPais.setValue(pais);

                    // Carregar províncies
                    controller.canviPais();
                });

        // -------------------------
        // PROVÍNCIA
        // -------------------------

        if (adreca.getProvincia() == null) {
            return;
        }

        Long provinciaId = adreca.getProvincia().getId();

        cmbProvincia.getItems().stream()
                .filter(provincia ->
                        provincia.getId().equals(provinciaId))
                .findFirst()
                .ifPresent(provincia -> {

                    cmbProvincia.setValue(provincia);

                    // Carregar poblacions
                    controller.canviProvincia();
                });

        // -------------------------
        // POBLACIÓ
        // -------------------------

        if (adreca.getPoblacio() == null) {
            return;
        }

        Long poblacioId = adreca.getPoblacio().getId();

        cmbPoblacio.getItems().stream()
                .filter(poblacio ->
                        poblacio.getId().equals(poblacioId))
                .findFirst()
                .ifPresent(cmbPoblacio::setValue);
    }










    public void actualitzar(Adreca adreca) {
        adreca.setCarrer(txtCarrer.getText());
        adreca.setNumero(txtNumero.getText());
        adreca.setPis(txtPis.getText());
        adreca.setPorta(txtPorta.getText());
        adreca.setPais(cmbPais.getValue());
        adreca.setProvincia(cmbProvincia.getValue());
        adreca.setPoblacio(cmbPoblacio.getValue());
    }

    public void registrarDirty(DirtyTracker tracker) {
        DirtyBindings.registrar(
                tracker,
                txtCarrer,
                txtNumero,
                txtPis,
                txtPorta,
                cmbPais,
                cmbProvincia,
                cmbPoblacio
        );
    }
}