package cat.informaticassa.icfact.actualitzacio.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InformacioActualitzacio {
    private String ultimaVersio;
    private Windows windows;
    private Linux linux;
    private List<String> notes;

    @Getter
    @Setter
    public static class Windows {
        private boolean disponible;
        private String url;
    }

    @Getter
    @Setter
    public static class Linux {
        private boolean disponible;
        private String url;
    }
}