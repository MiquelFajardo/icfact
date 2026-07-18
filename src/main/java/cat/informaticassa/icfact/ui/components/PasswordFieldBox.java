package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class PasswordFieldBox extends VBox {

    private final Label etiqueta;
    private final PasswordField passwordField;

    public PasswordFieldBox(String textEtiqueta) {
        setSpacing(6);
        setAlignment(Pos.TOP_LEFT);
        etiqueta = new Label(textEtiqueta);
        etiqueta.setFont(Tema.ETIQUETA);
        etiqueta.setTextFill(Tema.TEXT);
        passwordField = new PasswordField();
        passwordField.setFont(Tema.TEXT_NORMAL);
        passwordField.setPrefHeight(44);
        passwordField.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(passwordField, Priority.NEVER);
        getChildren().addAll(etiqueta, passwordField);
    }

    public String getText() {
        return passwordField.getText();
    }

    public void setText(String text) {
        passwordField.setText(text);
    }

    public void clear() {
        passwordField.clear();
    }

    public void requestFocusField() {
        passwordField.requestFocus();
    }

}