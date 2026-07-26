package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;

@Getter
public class PasswordFieldBox extends VBox {
    private final Label etiqueta;
    private final PasswordField passwordField;
    private final TextField textField;
    private final Button botoMostrar;

    public PasswordFieldBox(String textEtiqueta) {
        setSpacing(6);
        setAlignment(Pos.TOP_LEFT);

        etiqueta = new Label(textEtiqueta);
        etiqueta.setFont(Tema.ETIQUETA);
        etiqueta.setTextFill(Tema.TEXT);

        passwordField = new PasswordField();
        passwordField.setFont(Tema.TEXT_NORMAL);
        passwordField.setPrefHeight(44);

        textField = new TextField();
        textField.setFont(Tema.TEXT_NORMAL);
        textField.setPrefHeight(44);
        textField.setManaged(false);
        textField.setVisible(false);

        botoMostrar = new Button("👁");
        botoMostrar.setPrefWidth(45);
        botoMostrar.setPrefHeight(44);

        botoMostrar.setOnAction(e -> mostrarAmagar());

        StackPane camps = new StackPane(passwordField, textField);
        HBox.setHgrow(camps, Priority.ALWAYS);

        HBox fila = new HBox(5, camps, botoMostrar);

        getChildren().addAll(etiqueta, fila);
    }

    private void mostrarAmagar() {
        if (passwordField.isVisible()) {
            textField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            textField.setVisible(true);
            textField.setManaged(true);
            botoMostrar.setText("🙈");
        } else {
            passwordField.setText(textField.getText());
            textField.setVisible(false);
            textField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            botoMostrar.setText("👁");
        }
    }

    public String getText() {
        return passwordField.isVisible()
                ? passwordField.getText()
                : textField.getText();
    }

    public void setText(String text) {
        passwordField.setText(text);
        textField.setText(text);
    }

    public void clear() {
        passwordField.clear();
        textField.clear();
    }

    public void requestFocusField() {
        if (passwordField.isVisible()) {
            passwordField.requestFocus();
        } else {
            textField.requestFocus();
        }
    }

    public void setOnAction(EventHandler<ActionEvent> handler) {
        passwordField.setOnAction(handler);
        textField.setOnAction(handler);
    }
}