package cat.informaticassa.icfact.ui.util.dirty;

import cat.informaticassa.icfact.ui.util.Alerta;
import javafx.stage.Window;

public final class DirtyManager {
    private DirtyManager() {
    }

    public static boolean sortir(DirtyPage pagina, Window window) {
        if (pagina.getDirtyTracker().estaModificat()) {
            return true;
        }

        RespostaDirty resposta = Alerta.confirmarCanvis(window);

        switch (resposta) {
            case DESAR -> {
                return pagina.guardar();
            }
            case DESCARTAR -> {
                pagina.cancelar();
                return true;
            }
            case CANCELAR -> {
                return false;
            }
        }
        return false;
    }
}