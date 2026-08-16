package cat.informaticassa.icfact.infraestructura.validacio;

import cat.informaticassa.icfact.infraestructura.validacio.exception.*;

public final class Validador {

    private Validador() {    }

    public static void obligatori(String valor, String camp) {
        if (valor == null || valor.isBlank()) {
            throw new CampObligatoriException(camp);
        }
    }

    public static void longitudMaxima(String valor, int longitud, String camp) {
        if (valor != null && valor.length() > longitud) {
            throw new LongitudMaximaException(camp, longitud);
        }
    }

    public static void email(String email) {
        if (email == null || email.isBlank()) {
            return;
        }

        if (!email.matches(Regex.EMAIL)) {
            throw new EmailInvalidException(email);
        }
    }

    public static void telefon(String telefon) {
        if (telefon == null || telefon.isBlank()) {
            return;
        }

        if (!telefon.matches(Regex.TELEFON)) {
            throw new TelefonInvalidException(telefon);
        }
    }

    public static void nif(String nif) {
        if (nif == null || nif.isBlank()) {
            return;
        }

        boolean valid = nif.matches(Regex.NIF) || nif.matches(Regex.NIE) || nif.matches(Regex.CIF);

        if (!valid) {
            throw new NifInvalidException(nif);
        }
    }

    public static void iban(String iban) {
        if (iban == null || iban.isBlank()) {
            return;
        }

        if (!iban.matches(Regex.IBAN)) {
            throw new IbanInvalidException(iban);
        }
    }
}