package cat.informaticassa.icfact.ui.altaEmpresa.controller;

import cat.informaticassa.icfact.empresa.exception.EmpresaJaExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.service.CrearEmpresaService;
import cat.informaticassa.icfact.empresa.service.validar.ValidarContrasenya;
import cat.informaticassa.icfact.empresa.service.validar.ValidarEmpresa;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.altaEmpresa.mapper.EmpresaMapper;
import cat.informaticassa.icfact.ui.altaEmpresa.view.AltaEmpresaView;
import cat.informaticassa.icfact.ui.components.dialogs.ClauRecuperacioDialog;
import cat.informaticassa.icfact.ui.navigation.Navegador;
import cat.informaticassa.icfact.ui.util.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AltaEmpresaController {
    private static final Logger logger = LoggerFactory.getLogger(AltaEmpresaController.class);
    private final AltaEmpresaView view;
    private final Navegador navegador;
    private final CrearEmpresaService crearEmpresaService = new CrearEmpresaService();
    private final ValidarEmpresa validarEmpresa = new ValidarEmpresa();
    private final ValidarContrasenya validarContrasenya = new ValidarContrasenya();
    private final EmpresaMapper empresaMapper = new EmpresaMapper();

    public AltaEmpresaController(AltaEmpresaView view, Navegador navegador) {
        this.view = view;
        this.navegador = navegador;
        inicialitzar();
    }

    private void inicialitzar() {
        view.getBotoCrear().setDefaultButton(true);
        view.getContrasenya().setOnAction(e -> crearEmpresa());
        view.getBotoCrear().setOnAction(event -> crearEmpresa());
    }

    private void crearEmpresa() {
        Empresa empresa = empresaMapper.convertir(view);
        try {
            validar(empresa);
            String clauRecuperacio = crearEmpresaService.executar(empresa);
            ClauRecuperacioDialog dialog = new ClauRecuperacioDialog(empresa.getNom(), clauRecuperacio);
            dialog.initOwner(view.getScene().getWindow());
            dialog.showAndWait();
            navegador.setEmpresa(empresa);
            navegador.mostrarPrincipal();
        } catch (ValidacioException | EmpresaJaExisteixException e) {
            Alerta.error(view.getScene().getWindow(), e.getMessage());
        } catch (Exception e) {
            logger.error("Error en crear l'empresa.", e);
            Alerta.error(view.getScene().getWindow(),"S'ha produït un error en crear l'empresa.");
        }
    }

    private void validar(Empresa empresa) {
        validarEmpresa.executar(empresa);
        validarContrasenya.executar(view.getContrasenya().getText(), view.getRepetirContrasenya().getText());
    }
}