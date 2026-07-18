package cat.informaticassa.icfact.testdata;

public class TestDataLoader {

    private TestDataLoader() {
    }

    public static void carregar() {

        PaisTestData.carregar();
        ProvinciaTestData.carregar();
        PoblacioTestData.carregar();
        AdrecaTestData.carregar();

        EmpresaTestData.carregar();

        ClientTestData.carregar();

        IvaTestData.carregar();
        FormaPagamentTestData.carregar();
        ProducteTestData.carregar();

        PressupostTestData.carregar();

        FacturaTestData.carregar();
        PagamentTestData.carregar();
    }
}
