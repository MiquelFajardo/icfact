package cat.informaticassa.icfact.empresa.service.validar;

import cat.informaticassa.icfact.infraestructura.validacio.Validador;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;

public class ValidarContrasenya {
    public void executar(String contrasenya, String repetirContrasenya) {
        Validador.obligatori(contrasenya, "Contrasenya");
        if (!contrasenya.equals(repetirContrasenya)) {
            throw new ValidacioException("Les contrasenyes no coincideixen.");
        }
    }
}