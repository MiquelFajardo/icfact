package cat.informaticassa.icfact.client.repository;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.client.model.Client;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest extends BaseRepositoryTest {

    private final ClientRepository repository = new ClientRepository();

    @Test
    void buscarClientPerNif() {
        Optional<Client> resultat = repository.buscarPerNif("12345678A");
        assertTrue(resultat.isPresent());
        assertEquals("Joan Puig", resultat.get().getNom());
    }

    @Test
    void buscarClientPerNifInexistent() {
        Optional<Client> resultat = repository.buscarPerNif("00000000X");
        assertTrue(resultat.isEmpty());
    }

    @Test
    void buscarClientPerNom() {
        List<Client> clients = repository.buscarPerNom("Joan");
        assertEquals(1, clients.size());
    }

    @Test
    void buscarTotsElsClients() {
        List<Client> clients = repository.buscarTots();
        assertEquals(3, clients.size());
    }

    @Test
    void buscarTotsElsClientsActius() {
        List<Client> clients = repository.buscarTotsActius();
        assertEquals(3, clients.size());
    }

    @Test
    void actualitzarClient() {
        Client client = repository.buscarPerNif("B12345678").orElseThrow();
        client.setTelefon("972123456");
        repository.actualitzar(client);
        Optional<Client> resultat = repository.buscarPerNif("B12345678");
        assertTrue(resultat.isPresent());
        assertEquals("972123456", resultat.get().getTelefon());
    }

    @Test
    void desactivarClient() {
        Client client = repository.buscarPerNif("45678912C").orElseThrow();
        repository.eliminar(client);
        Optional<Client> resultat = repository.buscarPerNif("45678912C");
        assertTrue(resultat.isPresent());
        assertFalse(resultat.get().getActiu());
    }

    @Test
    void activarClient() {
        Client client = repository.buscarPerNif("45678912C").orElseThrow();
        repository.eliminar(client);
        repository.activar(client);
        Optional<Client> resultat = repository.buscarPerNif("45678912C");
        assertTrue(resultat.isPresent());
        assertTrue(resultat.get().getActiu());
    }
}