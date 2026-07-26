package cat.informaticassa.icfact.ui.util.dirty;

public interface DirtyPage {
    DirtyTracker getDirtyTracker();
    boolean guardar();
    void cancelar();
}
