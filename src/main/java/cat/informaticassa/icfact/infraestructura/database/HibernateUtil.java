package cat.informaticassa.icfact.infraestructura.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static String nomBaseDades;

    private HibernateUtil() {
    }

    public static void configurarBaseDades(String nom) {
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

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}