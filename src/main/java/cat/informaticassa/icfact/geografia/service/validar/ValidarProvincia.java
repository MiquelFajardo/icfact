package cat.informaticassa.icfact.geografia.service.validar;

import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.infraestructura.validacio.Validador;

public class ValidarProvincia {

    public void executar(Provincia provincia) {
        Validador.obligatori(provincia.getNom(), "Nom");
        Validador.longitudMaxima(provincia.getNom(), 100, "Nom");

        Validador.obligatori(provincia.getCodi(), "Codi");
        Validador.longitudMaxima(provincia.getCodi(), 10, "Codi");

        Validador.obligatori(String.valueOf(provincia.getPais()), "País");
    }
}