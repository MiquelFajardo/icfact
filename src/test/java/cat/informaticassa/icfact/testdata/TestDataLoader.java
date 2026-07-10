package cat.informaticassa.icfact.testdata;

public class TestDataLoader {
    private TestDataLoader() {
    }

    public static void carregar() {
        PaisTestData.carregar();
        ProvinciaTestData.carregar();
        PoblacioTestData.carregar();
    }
}
