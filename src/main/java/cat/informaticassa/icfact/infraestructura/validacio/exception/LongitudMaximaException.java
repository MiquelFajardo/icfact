package cat.informaticassa.icfact.infraestructura.validacio.exception;


public class LongitudMaximaException extends ValidacioException {

    public LongitudMaximaException(String camp, int longitud) {
        super("El camp '" + camp + "' no pot superar els " + longitud + " caràcters.");
    }
}