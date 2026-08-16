package cat.informaticassa.icfact.ui.util.dirty;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DirtyTracker {
    private final BooleanProperty modificat = new SimpleBooleanProperty(false);

    public void marcarModificat() {
        modificat.set(true);
    }

    public void marcarDesat() {
        modificat.set(false);
    }

    public boolean estaModificat() {
        return !modificat.get();
    }

    public BooleanProperty modificatProperty() {
        return modificat;
    }
}