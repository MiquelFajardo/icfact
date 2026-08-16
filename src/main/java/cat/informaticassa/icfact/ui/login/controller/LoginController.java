package cat.informaticassa.icfact.ui.login.controller;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.components.dialogs.RecuperarContrasenyaDialog;
import cat.informaticassa.icfact.ui.login.view.LoginView;
import cat.informaticassa.icfact.ui.navigation.Navegador;
import cat.informaticassa.icfact.ui.util.Alerta;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginView view;
    private final Navegador navegador;
    private final Empresa empresa;

    public LoginController(LoginView view, Navegador navegador, Empresa empresa) {
        this.view = view;
        this.navegador = navegador;
        this.empresa = empresa;
        inicialitzar();
    }

    private void inicialitzar() {
        view.getContrasenya().setOnAction(event -> entrar());
        view.getBotoEntrar().setOnAction(event -> entrar());
        view.getRecuperar().setOnAction(event -> recuperarContrasenya());
    }

    private void entrar() {
        String contrasenya = view.getContrasenya().getText();

        if (contrasenya.isBlank()) {
            Alerta.error(view.getScene().getWindow(), "Has d'introduir la contrasenya.");
            return;
        }

        try {
            if (!BCrypt.checkpw(contrasenya, empresa.getContrasenyaHash())) {
                Alerta.error(view.getScene().getWindow(), "La contrasenya és incorrecta.");
                view.getContrasenya().clear();
                view.getContrasenya().requestFocusField();
                return;
            }
            logger.info("Login correcte de l'empresa '{}'.", empresa.getNom());
            navegador.setEmpresa(empresa);
            navegador.mostrarPrincipal();
        } catch (Exception e) {
            logger.error("Error durant el procés de login.", e);
            Alerta.error(view.getScene().getWindow(), "S'ha produït un error en iniciar sessió.");
        }
    }

    private void recuperarContrasenya() {
        RecuperarContrasenyaDialog dialog = new RecuperarContrasenyaDialog(empresa);
        dialog.initOwner(view.getScene().getWindow());
        dialog.showAndWait();
    }
}