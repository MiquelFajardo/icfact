package cat.informaticassa.icfact.ui.tema;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Getter;

public final class Tema {

     private Tema() {
    }

    /*
     * Color principal
     */
    /*
     * Colors
     */
    @Getter
    private static Color colorPrincipal = Color.web("#2563EB");
    public static final Color FONS = Color.web("#F5F7FA");
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = Color.web("#1F2937");
    public static final Color TEXT_SECUNDARI = Color.web("#6B7280");
    public static final Color BORA = Color.web("#D1D5DB");
    public static final Color ERROR = Color.web("#DC2626");
    public static final Color EXIT = Color.web("#16A34A");
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
    public static final double FINESTRA_AMPLADA = 680;
    public static final double FINESTRA_ALCADA = 760;
    public static final double GAP = 16;
    public static final double BORDER_RADIUS = 18;

    /*
    * Cards
     */
    public static final double CARD_WIDTH = 650;
    public static final double CARD_RADIUS = 18;
    public static final double CARD_SPACING = 18;
    public static final Insets CARD_PADDING = new Insets(28);
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final double LOGO_SIZE = 72;
    public static final double TEXTFIELD_HEIGHT = 44;
    public static final double BUTTON_HEIGHT = 48;
    public static final Insets WINDOW_PADDING = new Insets(20);


    public static void setColorPrincipal(Color color) {
        if (color != null) {
            colorPrincipal = color;
        }
    }
}