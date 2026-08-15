package cat.informaticassa.icfact.infraestructura.database;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.tasca.model.Tasca;
import org.hibernate.cfg.Configuration;

public final class HibernateConfig {
    private HibernateConfig() {
    }

    public static Configuration getConfiguration(String nomBaseDades) {
        Configuration configuration = createConfiguration(nomBaseDades);

        configuration.addAnnotatedClass(Pais.class);
        configuration.addAnnotatedClass(Provincia.class);
        configuration.addAnnotatedClass(Poblacio.class);
        configuration.addAnnotatedClass(Adreca.class);

        configuration.addAnnotatedClass(Empresa.class);

        configuration.addAnnotatedClass(Client.class);

        configuration.addAnnotatedClass(Iva.class);
        configuration.addAnnotatedClass(Producte.class);
        configuration.addAnnotatedClass(FormaPagament.class);
        configuration.addAnnotatedClass(Pressupost.class);
        configuration.addAnnotatedClass(LiniaPressupost.class);

        configuration.addAnnotatedClass(Factura.class);
        configuration.addAnnotatedClass(LiniaFactura.class);
        configuration.addAnnotatedClass(Pagament.class);

        configuration.addAnnotatedClass(Tasca.class);
        return configuration;
    }

    private static Configuration createConfiguration(String nomBaseDades) {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
        configuration.setProperty("hibernate.connection.url","JDBC:sqlite:" + nomBaseDades);
        configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");  // Create esborra dades || update es poden afegir taules || validate no modifica taules
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        return configuration;
    }
}
