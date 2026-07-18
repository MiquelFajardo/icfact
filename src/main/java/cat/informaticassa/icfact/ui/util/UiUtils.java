package cat.informaticassa.icfact.ui.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Desktop;
import java.net.URI;

public final class UiUtils {
    private static final Logger logger = LoggerFactory.getLogger(UiUtils.class);

    private UiUtils() {
    }

    public static void obrirWeb(String url) {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("linux")) {
                new ProcessBuilder("xdg-open", url).start();
                return;
            }

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                return;
            }

            logger.warn("No s'ha pogut obrir el navegador. Sistema operatiu: {}", os);

        } catch (Exception ex) {
            logger.error("No s'ha pogut obrir {}", url, ex);
        }
    }
}