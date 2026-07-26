package cat.informaticassa.icfact.infraestructura.database;

import cat.informaticassa.icfact.app.AppConfig;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static String nomBaseDades;

    private HibernateUtil() {
    }

    public static void configurarBaseDades(String nom) {
        if (sessionFactory != null) {
            throw new IllegalStateException(
                    "No es pot canviar la base de dades quan la SessionFactory ja està creada.");
        }
        nomBaseDades = nom;
    }

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            if (nomBaseDades == null) {
                throw new IllegalStateException("Base de dades no configurada.");
            }

            Configuration configuration = HibernateConfig.getConfiguration(nomBaseDades);

            sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build());
        }
        return sessionFactory;
    }

    public static void configurarProduccio() {
        configurarBaseDades(AppConfig.getBaseDadesProduccio());
    }

    public static void configurarTest() {
        configurarBaseDades(AppConfig.getBaseDadesTest());
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}