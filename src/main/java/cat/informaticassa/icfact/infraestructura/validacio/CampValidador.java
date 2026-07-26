package cat.informaticassa.icfact.infraestructura.validacio;

import cat.informaticassa.icfact.infraestructura.validacio.exception.*;

public class CampValidador {

    private final String valor;
    private final String nomCamp;

    public CampValidador(String valor, String nomCamp) {
        this.valor = valor;
        this.nomCamp = nomCamp;
    }

    public CampValidador obligatori() {
        if (valor == null || valor.isBlank()) {
            throw new CampObligatoriException(nomCamp);
        }
        return this;
    }

    public CampValidador minim(int longitud) {
        if (valor != null && valor.length() < longitud) {
            throw new LongitudMinimaException(nomCamp, longitud);
        }
        return this;
    }

    public CampValidador maxim(int longitud) {
        if (valor != null && valor.length() > longitud) {
            throw new LongitudMaximaException(nomCamp, longitud);
        }
        return this;
    }

    public CampValidador email() {
        if (valor == null || valor.isBlank()) {
            return this;
        }

        if (!valor.matches(Regex.EMAIL)) {
            throw new EmailInvalidException(valor);
        }
        return this;
    }

    public CampValidador telefon() {
        if (valor == null || valor.isBlank()) {
            return this;
        }

        if (!valor.matches(Regex.TELEFON)) {
            throw new TelefonInvalidException(valor);
        }
        return this;
    }

    public CampValidador nif() {
        if (valor == null || valor.isBlank()) {
            return this;
        }

        boolean valid = valor.matches(Regex.NIF) || valor.matches(Regex.NIE) || valor.matches(Regex.CIF);

        if (!valid) {
            throw new NifInvalidException(valor);
        }
        return this;
    }

    public CampValidador iban() {
        if (valor == null || valor.isBlank()) {
            return this;
        }

        if (!valor.matches(Regex.IBAN)) {
            throw new IbanInvalidException(valor);
        }
        return this;
    }
}