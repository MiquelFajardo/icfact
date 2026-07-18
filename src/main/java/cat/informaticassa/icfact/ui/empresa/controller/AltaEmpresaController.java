package cat.informaticassa.icfact.ui.empresa.controller;

import cat.informaticassa.icfact.empresa.exception.EmpresaJaExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.service.CrearEmpresaService;
import cat.informaticassa.icfact.empresa.validation.ValidadorEmpresa;
import cat.informaticassa.icfact.ui.empresa.view.AltaEmpresaView;
import cat.informaticassa.icfact.ui.navigation.Navegador;
import cat.informaticassa.icfact.ui.util.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AltaEmpresaController {
    private static final Logger logger = LoggerFactory.getLogger(AltaEmpresaController.class);
    private final AltaEmpresaView view;
    private final Navegador navegador;
    private final ValidadorEmpresa validador = new ValidadorEmpresa();
    private final CrearEmpresaService crearEmpresaService = new CrearEmpresaService();

    public AltaEmpresaController(AltaEmpresaView view, Navegador navegador) {
        this.view = view;
        this.navegador = navegador;
        inicialitzar();
    }

    private void inicialitzar() {
        view.getBotoCrear().setOnAction(event -> crearEmpresa());
    }

    private void crearEmpresa() {
        String error = validador.validar(view);

        if (error != null) {
            Alerta.error(view.getScene().getWindow(),error);
            return;
        }

        Empresa empresa = new Empresa();
        empresa.setNom(view.getNomEmpresa().getText().trim());
        empresa.setNif(view.getNif().getText().trim());
        empresa.setDescripcio(view.getDescripcio().getText().trim());
        empresa.setTelefon(view.getTelefon().getText().trim());
        empresa.setEmail(view.getEmail().getText().trim());
        // El servei generarà el hash BCrypt.
        empresa.setContrasenyaHash(view.getContrasenya().getText());

        try {
            crearEmpresaService.executar(empresa);
            Alerta.informacio(view.getScene().getWindow(),"Empresa creada", "L'empresa s'ha creat correctament");
            navegador.mostrarPrincipal();
        } catch (EmpresaJaExisteixException e) {
            Alerta.error(view.getScene().getWindow(),e.getMessage());
        } catch (Exception e) {
            logger.error("Error en crear l'empresa.", e);
            Alerta.error(view.getScene().getWindow(),"S'ha produït un error en crear l'empresa.");
        }
    }
}