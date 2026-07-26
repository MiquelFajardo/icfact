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
        if (AppConfig.esborrarBaseDadesTest()) {
            try {
                Files.deleteIfExists(Path.of(AppConfig.getBaseDadesTest()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        HibernateUtil.configurarTest();
        HibernateUtil.getSessionFactory();
        TestDataLoader.carregar();
    }

    @AfterEach
    void finalitzar() {
        HibernateUtil.shutdown();
    }
}