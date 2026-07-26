package cat.informaticassa.icfact.infraestructura.validacio.exception;

public class ValidacioException extends RuntimeException {
    public ValidacioException(String missatge) {
        super(missatge);
    }
}