package cat.informaticassa.icfact.infraestructura.validacio.exception;

public class ValorInvalidException extends ValidacioException {
    public ValorInvalidException(String camp) {
        super("El valor del camp '" + camp + "' no és vàlid.");
    }
}
