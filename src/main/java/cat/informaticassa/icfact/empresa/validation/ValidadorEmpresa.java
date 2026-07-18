package cat.informaticassa.icfact.empresa.validation;

import cat.informaticassa.icfact.ui.empresa.view.AltaEmpresaView;

public class ValidadorEmpresa {

    public String validar(AltaEmpresaView view) {
        String nom = view.getNomEmpresa().getText().trim();

        if (nom.isEmpty()) {
            return "Has d'introduir el nom de l'empresa.";
        }

        String nif = view.getNif().getText().trim();

        if (nif.isEmpty()) {
            return "Has d'introduir el NIF.";
        }

        String contrasenya = view.getContrasenya().getText();

        if (contrasenya.isBlank()) {
            return "Has d'introduir una contrasenya.";
        }

        String repetir = view.getRepetirContrasenya().getText();

        if (!contrasenya.equals(repetir)) {
            return "Les contrasenyes no coincideixen.";
        }

        return null;

    }

}