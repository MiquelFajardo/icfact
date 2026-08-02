package cat.informaticassa.icfact.geografia.service.validar;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.infraestructura.validacio.Validador;

public class ValidarPoblacio {

    public void executar(Poblacio poblacio) {

        Validador.obligatori(poblacio.getNom(), "Nom");
        Validador.longitudMaxima(poblacio.getNom(), 100, "Nom");

        Validador.obligatori(poblacio.getCodiPostal().toString(), "Codi postal");
        Validador.longitudMaxima(poblacio.getCodiPostal().toString(), 10, "Codi postal");

        Validador.obligatori(String.valueOf(poblacio.getProvincia()), "Província");
    }
}