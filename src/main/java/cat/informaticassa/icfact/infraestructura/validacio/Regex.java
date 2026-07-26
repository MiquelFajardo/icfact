package cat.informaticassa.icfact.infraestructura.validacio;

public final class Regex {

    private Regex() {
    }

    public static final String EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static final String TELEFON = "^[+]?[0-9 ]{6,20}$";

    public static final String NIF = "^[0-9]{8}[A-Za-z]$";

    public static final String CIF = "^[ABCDEFGHJNPQRSUVW][0-9]{7}[0-9A-J]$";

    public static final String NIE = "^[XYZ][0-9]{7}[A-Za-z]$";

    public static final String IBAN = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$";

    public static final String CODI_POSTAL = "^[0-9]{5}$";
}