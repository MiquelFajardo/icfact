package cat.informaticassa.icfact;

import cat.informaticassa.icfact.empresa.service.EmpresaExisteixService;
import cat.informaticassa.icfact.ui.navigation.Navegador;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Inicialitzador.inicialitzar();
        Navegador navegador = new Navegador(stage);

        if (new EmpresaExisteixService().executar()) {
            navegador.mostrarLogin();
        } else {
            navegador.mostrarAltaEmpresa();
        }
        navegador.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}