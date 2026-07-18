package cat.informaticassa.icfact;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;

public final class Inicialitzador {
    private static final String BASE_DADES = "empresa.db";
    private Inicialitzador() {
    }

    public static void inicialitzar() {
        HibernateUtil.configurarBaseDades(ConstantsAplicacio.BASE_DADES);
    }
}