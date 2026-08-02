package cat.informaticassa.icfact.ui.components;

import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class FormDecimalField extends TextField {
    public FormDecimalField() {
        textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            String valor = newValue.replace(',', '.');
            if (!valor.matches("\\d*(\\.\\d{0,2})?")) {
                setText(oldValue);
            }
        });
    }

    public BigDecimal getValue() {
        String text = getText();
        if (text == null || text.isBlank()) {
            return null;
        }
        return new BigDecimal(text.replace(',', '.'));
    }

    public void setValue(BigDecimal value) {
        if (value == null) {
            clear();
        } else {
            setText(value.stripTrailingZeros().toPlainString());
        }
    }
}