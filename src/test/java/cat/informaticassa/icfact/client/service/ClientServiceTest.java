package cat.informaticassa.icfact.client.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.client.exception.ClientJaExisteixException;
import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest extends BaseRepositoryTest {

    private final ClientRepository repository = new ClientRepository();

    @Test
    void guardarClient() {
        Client client = Client.builder()
                .nom("Nou Client")
                .nif("99999999Z")
                .adreca(repository.buscarPerNif("12345678A").orElseThrow().getAdreca())
                .telefon("600000000")
                .email("nou@test.cat")
                .web("www.test.cat")
                .build();
        new GuardarClientService().executar(client);
        assertTrue(repository.buscarPerNif("99999999Z").isPresent());

        Client resultat = repository.buscarPerNif("99999999Z").orElseThrow();
        assertTrue(resultat.isActiu());
        assertNotNull(resultat.getDataCreacio());
        assertNotNull(resultat.getDataModificacio());
    }

    @Test
    void guardarClientQuanJaExisteix() {
        Client client = repository.buscarPerNif("12345678A").orElseThrow();
        assertThrows(ClientJaExisteixException.class,
                () -> new GuardarClientService().executar(client));
    }

    @Test
    void actualitzarClient() {
        Client client = repository.buscarPerNif("12345678A").orElseThrow();
        client.setTelefon("972123456");
        new ActualitzarClientService().executar(client);
        Client resultat = repository.buscarPerNif("12345678A").orElseThrow();
        assertEquals("972123456", resultat.getTelefon());
    }

    @Test
    void buscarTots() {
        assertEquals(3, new BuscarClientService().buscarTots().size());
    }

    @Test
    void buscarActius() {
        assertEquals(3, new BuscarClientService().buscarActius().size());
    }

    @Test
    void buscarInactius() {
        Client client = repository.buscarPerNif("12345678A").orElseThrow();
        new DesactivarClientService().executar(client);
        assertEquals(1, new BuscarClientService().buscarInactius().size());
    }

    @Test
    void buscarClientPerNif() {
        Client client = new BuscarClientService().buscarPerNif("12345678A");
        assertNotNull(client);
    }

    @Test
    void buscarClientPerId() {
        Client origen = repository.buscarPerNif("12345678A").orElseThrow();
        Client client = new BuscarClientService().buscarPerId(origen.getId());
        assertNotNull(client);
    }

    @Test
    void desactivarClient() {
        Client client = repository.buscarPerNif("12345678A").orElseThrow();
        new DesactivarClientService().executar(client);
        Client resultat = repository.buscarPerIdIncloentInactius(client.getId()).orElseThrow();
        assertFalse(resultat.isActiu());
    }

    @Test
    void activarClient() {
        Client client = repository.buscarPerNif("12345678A").orElseThrow();
        new DesactivarClientService().executar(client);
        new ActivarClientService().executar(client);
        Client resultat = repository.buscarPerIdIncloentInactius(client.getId()).orElseThrow();
        assertTrue(resultat.isActiu());
    }
}
