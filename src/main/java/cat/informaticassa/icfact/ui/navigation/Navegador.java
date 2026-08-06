package cat.informaticassa.icfact.ui.navigation;

import cat.informaticassa.icfact.BuildInfo;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.service.BuscarEmpresaService;
import cat.informaticassa.icfact.ui.altaEmpresa.view.AltaEmpresaView;
import cat.informaticassa.icfact.ui.login.view.LoginView;
import cat.informaticassa.icfact.ui.main.view.MainView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Navegador {
    private final Stage stage;
    @Getter
    @Setter
    private Empresa empresa;
    @Getter
    private MainView mainView;

    public Navegador(Stage stage) {
        this.stage = stage;
        configurarStage();
    }

    private void configurarStage() {
        stage.setTitle(BuildInfo.getNomAplicacio());
        stage.getIcons().add( new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/logo.png"))));
    }

    private void midaAltaEmpresa() {
        stage.setMaximized(false);
        stage.setWidth(1100);
        stage.setHeight(850);
        stage.centerOnScreen();
    }

    private void midaLogin() {
        stage.setMaximized(false);
        stage.setWidth(700);
        stage.setHeight(600);
        stage.centerOnScreen();
    }

    public void mostrarAltaEmpresa() {
        midaAltaEmpresa();
        mostrar(new AltaEmpresaView(this));
    }

    public void mostrarLogin() {
        midaLogin();
        Empresa empresa = new BuscarEmpresaService().executar();
        mostrar(new LoginView(this, empresa));
    }

    private void mostrar(Parent vista) {
        stage.setScene(new Scene(vista));
    }

    public void mostrar() {
        stage.show();
    }

    public void mostrarPrincipal() {
        mainView = new MainView(empresa);
        mostrar(mainView);
        stage.show();
        stage.setMaximized(true);
    }
}