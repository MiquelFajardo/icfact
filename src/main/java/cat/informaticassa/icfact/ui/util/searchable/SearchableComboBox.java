package cat.informaticassa.icfact.ui.util.searchable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class SearchableComboBox {

    private SearchableComboBox() {
    }

    public static <T> void activar(            ComboBox<T> comboBox,
            Function<T, String> searchFunction) {
        ObservableList<T> itemsOriginals = FXCollections.observableArrayList(comboBox.getItems());
        comboBox.setEditable(true);
        comboBox.getEditor().textProperty().addListener((obs, antic, nou) -> {
            if (!comboBox.isFocused()) {
                return;
            }
            String filtre = nou == null
                    ? ""
                    : nou.toLowerCase().trim();
            List<T> resultat = new ArrayList<>();
            for (T item : itemsOriginals) {
                String text = searchFunction.apply(item);
                if (text != null && text.toLowerCase().contains(filtre)) {
                    resultat.add(item);
                }
            }
            comboBox.getItems().setAll(resultat);
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
        });

        comboBox.focusedProperty().addListener((obs, antic, focus) -> {
            if (focus) {
                comboBox.getItems().setAll(itemsOriginals);
                return;
            }
            comboBox.getItems().setAll(itemsOriginals);
        });
    }
}