package cat.informaticassa.icfact.ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class Icones {

    private Icones() {
    }

    private static Label crear(String emoji) {

        Label label = new Label(emoji);

        label.setAlignment(Pos.CENTER);

        label.setFont(Font.font("Segoe UI Emoji", FontWeight.NORMAL, 20));

        return label;
    }

    public static Label client() {
        return crear("👤");
    }

    public static Label empresa() {
        return crear("🏢");
    }

    public static Label pressupost() {
        return crear("📄");
    }

    public static Label factura() {
        return crear("🧾");
    }

    public static Label cobrament() {
        return crear("💶");
    }

    public static Label producte() {
        return crear("📦");
    }

    public static Label article() {
        return crear("🏷");
    }

    public static Label proveidor() {
        return crear("🚚");
    }

    public static Label usuari() {
        return crear("👥");
    }

    public static Label configuracio() {
        return crear("⚙");
    }

    public static Label pais() {
        return crear("🌍");
    }

    public static Label provincia() {
        return crear("🗺");
    }

    public static Label poblacio() {
        return crear("📍");
    }

    public static Label adreca() {
        return crear("🏠");
    }
}