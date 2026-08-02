package cat.informaticassa.icfact.geografia.service.validar;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.infraestructura.validacio.Validador;

public class ValidarPais {

    public void executar(Pais pais) {
        Validador.obligatori(pais.getNom(), "Nom");
        Validador.longitudMaxima(pais.getNom(), 100, "Nom");
        Validador.obligatori(pais.getCodiIso(), "Codi ISO");
        Validador.longitudMaxima(pais.getCodiIso(), 2, "Codi ISO");
    }
}