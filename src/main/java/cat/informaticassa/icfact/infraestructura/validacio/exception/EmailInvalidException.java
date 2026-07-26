package cat.informaticassa.icfact.infraestructura.validacio.exception;

public class EmailInvalidException extends ValidacioException {
    public EmailInvalidException(String email) {
        super("L'adreça de correu '" + email + "' no és vàlida.");
    }
}