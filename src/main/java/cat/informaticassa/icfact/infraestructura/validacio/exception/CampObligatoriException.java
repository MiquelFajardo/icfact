package cat.informaticassa.icfact.infraestructura.validacio.exception;

public class CampObligatoriException extends ValidacioException {
    public CampObligatoriException(String camp) {
        super("El camp '" + camp + "' és obligatori.");
    }
}