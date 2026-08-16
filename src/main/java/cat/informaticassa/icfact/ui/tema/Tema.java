package cat.informaticassa.icfact.ui.tema;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class Tema {

     private Tema() {
    }

    /*
     * Color principal
     */
    /*
     * Colors
     */
    private static final ObjectProperty<Color> colorPrincipal = new SimpleObjectProperty<>(Color.web("#2563EB"));
    public static final Color FONS = Color.web("#F5F7FA");
    public static final Color TEXT = Color.web("#1F2937");
    public static final Color TEXT_SECUNDARI = Color.web("#6B7280");
    public static final Color BORDER = Color.web("#D1D5DB");
    public static final Color SIDEBAR = Color.web("#1F2937");
    /*
     * Fonts
     */
    public static final Font TITOL = Font.font("Inter", FontWeight.BOLD, 30);
    public static final Font SUBTITOL = Font.font("Inter", 16);
    public static final Font ETIQUETA = Font.font("Inter", FontWeight.SEMI_BOLD, 13);
    public static final Font TEXT_NORMAL = Font.font("Inter", 14);

    /*
     * Mides
     */
    public static final double BORDER_RADIUS = 18;

    /*
    * Cards
     */
    public static final Font TITOL_CARD = Font.font("Inter", FontWeight.BOLD, 20);
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static Color getColorPrincipal() {
        return colorPrincipal.get();
    }
    public static ObjectProperty<Color> colorPrincipalProperty() {
        return colorPrincipal;
    }

    public static void setColorPrincipal(Color color) {
        if (color != null) {
            colorPrincipal.set(color);
        }
    }
}