package cat.informaticassa.icfact.infraestructura.database;
import cat.informaticassa.icfact.app.AppConfig;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import org.hibernate.cfg.Configuration;

public final class HibernateConfig {
    private HibernateConfig() {
    }

    public static Configuration getConfiguration(String nomBaseDades) {
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
        configuration.setProperty("hibernate.connection.url","JDBC:sqlite:" + nomBaseDades);

        configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");

        configuration.setProperty("hibernate.hbm2ddl.auto", "create");

        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");

        configuration.addAnnotatedClass(Pais.class);
        configuration.addAnnotatedClass(Provincia.class);
        configuration.addAnnotatedClass(Poblacio.class);
        configuration.addAnnotatedClass(Adreca.class);

        configuration.addAnnotatedClass(Empresa.class);

        return configuration;
    }
}
