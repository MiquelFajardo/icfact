package cat.informaticassa.icfact.ui.util.searchable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;

import java.util.function.Function;

class SearchPopup<T> {
    private final SearchField<T> field;
    private final Popup popup = new Popup();
    private final ListView<T> listView = new ListView<>();

    SearchPopup(SearchField<T> field) {
        this.field = field;
        popup.setAutoHide(true);
        listView.setPrefHeight(250);
        listView.setPrefWidth(400);
        popup.getContent().add(listView);
        listView.setCellFactory(v -> new SearchCell<>(field));
        listView.setOnMouseClicked(e -> acceptSelection());
    }

    void filter(String text,
                ObservableList<T> items,
                Function<T,String> searchFunction) {
        if (text == null) {
            text = "";
        }
        String filtre = text.toLowerCase().trim();
        ObservableList<T> resultat = FXCollections.observableArrayList();
        for (T item : items) {
            String valor = searchFunction.apply(item);
            if (valor != null && valor.toLowerCase().contains(filtre)) {
                resultat.add(item);
            }
        }

        listView.setItems(resultat);
        if (resultat.isEmpty()) {
            popup.hide();
            return;
        }

        if (!popup.isShowing()) {
            popup.show(field, field.localToScreen(0, field.getHeight()).getX(),field.localToScreen(0, field.getHeight()).getY());
        }
    }

    void acceptSelection() {
        T item = listView.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }
        field.setSelectedItem(item);
        popup.hide();
        field.fireItemSelected();
    }

    void handleKey(KeyEvent event) {
        if (!popup.isShowing()) {
            return;
        }
        if (event.getCode() == KeyCode.DOWN) {
            listView.requestFocus();
            listView.getSelectionModel().selectFirst();
            event.consume();
            return;
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            popup.hide();
            event.consume();
        }
    }

    void hide() {
        popup.hide();
    }
}