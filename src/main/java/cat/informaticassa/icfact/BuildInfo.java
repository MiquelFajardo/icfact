package cat.informaticassa.icfact;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class BuildInfo {

    private static final Properties properties = new Properties();

    static {
        try (InputStream in = BuildInfo.class.getResourceAsStream("/build.properties")) {
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException ignored) {
        }
    }

    private BuildInfo() {
    }

    public static String getNomAplicacio() {
        return properties.getProperty("app.name", "ICFact");
    }

    public static String getVersio() {
        return properties.getProperty("app.version", "desenvolupament");
    }

}