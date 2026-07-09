package cat.informaticassa.icfact;


import cat.informaticassa.icfact.app.AppConfig;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.testdata.TestDataLoader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BaseRepositoryTest {

    @BeforeAll
    static void inicialitzar() {
        if (AppConfig.ESBORRAR_BD_TEST) {
            try {
                Files.deleteIfExists(Path.of(AppConfig.NOM_BASE_DADES_TEST));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        HibernateUtil.configurarBaseDades(AppConfig.NOM_BASE_DADES_TEST);
        HibernateUtil.getSessionFactory();

        TestDataLoader.carregar();
    }



    @AfterAll
    static void finalitzar() {
        HibernateUtil.shutdown();
    }
}