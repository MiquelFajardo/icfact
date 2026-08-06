package cat.informaticassa.icfact.ui.util.searchable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.function.Function;
public class SearchField<T> extends TextField {
    @Getter
    private final ObservableList<T> items = FXCollections.observableArrayList();
    private final ObjectProperty<T> selectedItem = new SimpleObjectProperty<>();
    @Setter
    private Function<T, String> searchFunction = Object::toString;
    @Getter
    @Setter
    private Function<T, String> displayFunction = Object::toString;
    private final SearchPopup<T> popup;
    @Setter
    private EventHandler<ActionEvent> onItemSelected;

    public SearchField() {
        popup = new SearchPopup<>(this);
        textProperty().addListener((obs, oldValue, newValue) -> {
            if (!isFocused()) {
                return;
            }
            popup.filter(newValue, items, searchFunction);
        });

        focusedProperty().addListener((obs, oldValue, focused) -> {
            if (!focused) {
                popup.hide();
            }
        });
        setOnAction(e -> popup.acceptSelection());
        setOnKeyPressed(popup::handleKey);
    }

    public void setItems(Collection<T> values) {
        items.setAll(values);
    }

    public T getSelectedItem() {
        return selectedItem.get();
    }

    public void setSelectedItem(T item) {
        selectedItem.set(item);
        if (item == null) {
            clear();
        } else {
            setText(displayFunction.apply(item));
        }
    }

    void fireItemSelected() {
        if (onItemSelected != null) {
            onItemSelected.handle(new ActionEvent(this, this));
        }
    }

    ObjectProperty<T> selectedItemProperty() {
        return selectedItem;
    }
}