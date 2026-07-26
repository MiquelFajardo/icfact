package cat.informaticassa.icfact.infraestructura.validacio.exception;

public class LongitudMinimaException extends ValidacioException {
    public LongitudMinimaException(String camp, int longitud) {
        super("El camp '" + camp + "' ha de tenir almenys " + longitud + " caràcters.");
    }
}
