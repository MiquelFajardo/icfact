package cat.informaticassa.icfact.app;

public final class AppConfig {

    public static final String NOM_BASE_DADES = "empresa.db";
    public static final String NOM_BASE_DADES_TEST = "empresa-test.db";
    public static final String NOM_LOG = "icfact.log";

    private AppConfig() {
    }

    public static String getBaseDadesProduccio() {
        return NOM_BASE_DADES;
    }

    public static String getBaseDadesTest() {
        return NOM_BASE_DADES_TEST;
    }

    public static boolean esborrarBaseDadesTest() {
        return true;
    }

}