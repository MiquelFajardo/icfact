package cat.informaticassa.icfact;


import cat.informaticassa.icfact.app.AppConfig;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.testdata.TestDataLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BaseRepositoryTest {

    @BeforeEach
    void inicialitzar() {
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

    @AfterEach
    void finalitzar() {
        HibernateUtil.shutdown();
    }
}