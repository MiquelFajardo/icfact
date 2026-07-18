package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.ui.tema.Tema;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class FormField extends VBox {

    private final Label etiqueta;
    private final TextField textField;

    public FormField(String textEtiqueta) {
        setSpacing(6);
        setAlignment(Pos.TOP_LEFT);
        etiqueta = new Label(textEtiqueta);
        etiqueta.setFont(Tema.ETIQUETA);
        etiqueta.setTextFill(Tema.TEXT);
        textField = new TextField();
        textField.setFont(Tema.TEXT_NORMAL);
        textField.setPrefHeight(44);
        textField.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(textField, Priority.NEVER);
        getChildren().addAll(etiqueta, textField);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public void clear() {
        textField.clear();
    }

    public void setPromptText(String text) {
        textField.setPromptText(text);
    }

    public void setEditable(boolean editable) {
        textField.setEditable(editable);
    }

    public void requestFocusField() {
        textField.requestFocus();
    }

}