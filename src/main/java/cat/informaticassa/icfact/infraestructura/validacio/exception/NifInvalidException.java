package cat.informaticassa.icfact.infraestructura.validacio.exception;

public class NifInvalidException extends ValidacioException {
    public NifInvalidException(String nif) {
        super("El NIF '" + nif + "' no és vàlid.");
    }
}