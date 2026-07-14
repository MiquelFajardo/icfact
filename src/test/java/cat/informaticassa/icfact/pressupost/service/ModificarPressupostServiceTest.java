package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModificarPressupostServiceTest extends BaseRepositoryTest {

    private final PressupostRepository pressupostRepository = new PressupostRepository();
    private final ClientRepository clientRepository = new ClientRepository();

    private final ModificarPressupostService service = new ModificarPressupostService();

    @Test
    void modificarPressupost() {

        Pressupost pressupost = pressupostRepository
                .buscarPerNumero("P2026000001")
                .orElseThrow();

        pressupost.setObservacions("Observacions modificades");
        pressupost.setData(LocalDate.of(2026, 7, 20));

        pressupost.setClient(
                clientRepository.buscarPerNif("45678912C").orElseThrow()
        );

        Pressupost modificat = service.executar(pressupost);

        assertEquals("Observacions modificades", modificat.getObservacions());
        assertEquals(LocalDate.of(2026, 7, 20), modificat.getData());
        assertEquals("45678912C", modificat.getClient().getNif());
    }
}