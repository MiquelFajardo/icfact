package cat.informaticassa.icfact.empresa.service.validar;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.infraestructura.validacio.Validador;

public class ValidarEmpresa {

    public void executar(Empresa empresa) {
        Validador.obligatori(empresa.getNom(), "Nom");
        Validador.longitudMaxima(empresa.getNom(), 150, "Nom");

        Validador.obligatori(empresa.getNif(), "NIF");
        Validador.nif(empresa.getNif());

        Validador.longitudMaxima(empresa.getDescripcio(), 150, "Descripció");

        Validador.telefon(empresa.getTelefon());

        Validador.email(empresa.getEmail());

        Validador.longitudMaxima(empresa.getWeb(), 150, "Web");

        Validador.iban(empresa.getIban());
    }
}