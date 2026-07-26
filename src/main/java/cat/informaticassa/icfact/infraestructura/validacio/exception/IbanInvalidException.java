package cat.informaticassa.icfact.infraestructura.validacio.exception;

public class IbanInvalidException extends ValidacioException {
    public IbanInvalidException(String iban) {
        super("El '" + iban + "' no és vàlid.");
    }
}
