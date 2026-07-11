package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.repository.AdrecaRepository;

import java.time.LocalDateTime;

public final class ClientTestData {

    private static final ClientRepository repository = new ClientRepository();
    private static final AdrecaRepository adrecaRepository = new AdrecaRepository();

    private ClientTestData() {
    }

    public static void carregar() {

        var adreces = adrecaRepository.buscarTots();

        guardar("Joan Puig", "12345678A", adreces.get(0), "600111111", "joan@test.cat");
        guardar("Pintures Garcia SL", "B12345678", adreces.get(1), "600222222", "info@garcia.cat");
        guardar("Fusteria Serra", "45678912C", adreces.get(2), "600333333", "contacte@serra.cat");

    }

    private static void guardar(String nom,
                                String nif,
                                Adreca adreca,
                                String telefon,
                                String email) {

        Client client = Client.builder()
                .nom(nom)
                .nif(nif)
                .adreca(adreca)
                .telefon(telefon)
                .email(email)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();

        repository.guardar(client);

    }
}