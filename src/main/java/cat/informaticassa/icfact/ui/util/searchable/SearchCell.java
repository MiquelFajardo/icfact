package cat.informaticassa.icfact.ui.util.searchable;

import javafx.scene.control.ListCell;

class SearchCell<T> extends ListCell<T> {
    private final SearchField<T> field;

    SearchCell(SearchField<T> field) {
        this.field = field;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            return;
        }
        setText(field.getDisplayFunction().apply(item));
    }
}