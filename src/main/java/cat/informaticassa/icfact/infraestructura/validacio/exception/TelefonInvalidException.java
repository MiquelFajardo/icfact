package cat.informaticassa.icfact.infraestructura.validacio.exception;

public class TelefonInvalidException extends ValidacioException {
    public TelefonInvalidException(String telefon) {
        super("El telèfon '" + telefon + "' no és vàlid.");
    }
}