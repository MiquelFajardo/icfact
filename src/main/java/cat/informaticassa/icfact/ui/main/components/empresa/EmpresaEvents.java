package cat.informaticassa.icfact.ui.main.components.empresa;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.service.BuscarEmpresaService;
import cat.informaticassa.icfact.empresa.service.ModificarEmpresaService;
import cat.informaticassa.icfact.infraestructura.validacio.exception.ValidacioException;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.BotoSecundari;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.Alerta;
import cat.informaticassa.icfact.ui.util.dirty.DirtyPage;
import cat.informaticassa.icfact.ui.util.dirty.DirtyTracker;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpresaEvents implements DirtyPage {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaEvents.class);

    private final FormulariEmpresa formulari;
    private final Empresa empresa;

    private final BotoPrimari botoGuardar;
    private final BotoSecundari botoCancelar;

    private final ModificarEmpresaService modificarEmpresaService = new ModificarEmpresaService();
    private final BuscarEmpresaService buscarEmpresaService = new BuscarEmpresaService();

    public EmpresaEvents(FormulariEmpresa formulari, Empresa empresa, BotoPrimari botoGuardar, BotoSecundari botoCancelar) {
        this.formulari = formulari;
        this.empresa = empresa;
        this.botoGuardar = botoGuardar;
        this.botoCancelar = botoCancelar;
        carregar();
        formulari.getDirtyTracker().marcarDesat();
        botoGuardar.setDisable(true);
        botoCancelar.setDisable(true);
        botoCancelar.destacar(false);

        formulari.getDirtyTracker()
                .modificatProperty()
                .addListener((obs, oldValue, modificat) -> {
                    botoGuardar.setDisable(!modificat);
                    botoCancelar.setDisable(!modificat);
                    botoCancelar.destacar(modificat);
                });

        botoGuardar.setOnAction(e -> guardar());
        botoCancelar.setOnAction(e -> cancelar());
    }

    private void carregar() {
        formulari.getEmpresaBinder().carregar(empresa);
        if (empresa.getAdreca() != null) {
            formulari.getAdrecaBinder().carregar(empresa.getAdreca());
        }
    }

    @Override
    public boolean guardar() {
        try {
            formulari.getEmpresaBinder().actualitzar(empresa);
            if (empresa.getAdreca() != null) {
                formulari.getAdrecaBinder().actualitzar(empresa.getAdreca());
            }

            modificarEmpresaService.executar(empresa);

            if (empresa.getColor() != null && !empresa.getColor().isBlank()) {
                Tema.setColorPrincipal(Color.web(empresa.getColor()));
            }
            formulari.getDirtyTracker().marcarDesat();
            Alerta.informacio("Empresa","Les dades s'han desat correctament.");
            return true;
        } catch (ValidacioException e) {
            Alerta.error(e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Error en desar les dades de l'empresa.", e);
            Alerta.error("S'ha produït un error inesperat.");
            return false;
        }
    }

    @Override
    public void cancelar() {
        boolean confirmar = Alerta.confirmar("Descartar canvis","Els canvis que has fet no s'han desat.\n\nVols descartar-los?");
        if (!confirmar) {
            return;
        }

        Empresa empresaBD = buscarEmpresaService.executar();
        formulari.getEmpresaBinder().copiar(empresaBD, empresa);
        carregar();
        formulari.getDirtyTracker().marcarDesat();
    }

    @Override
    public DirtyTracker getDirtyTracker() {
        return formulari.getDirtyTracker();
    }
}