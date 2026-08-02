package cat.informaticassa.icfact.client.service.validar;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.infraestructura.validacio.Validador;

public class ValidarClient {

    public void executar(Client client) {

        Validador.obligatori(client.getNom(), "Nom");
        Validador.longitudMaxima(client.getNom(), 150, "Nom");
        Validador.obligatori(client.getNif(), "NIF");
        Validador.nif(client.getNif());
        Validador.telefon(client.getTelefon());
        Validador.email(client.getEmail());
        Validador.longitudMaxima(client.getWeb(), 150, "Web");
    }
}