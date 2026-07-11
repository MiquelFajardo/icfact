package cat.informaticassa.icfact.infraestructura.database;

public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() {

        HibernateUtil.getSessionFactory();

    }

}
