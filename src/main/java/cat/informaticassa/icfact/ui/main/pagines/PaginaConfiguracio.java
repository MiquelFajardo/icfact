package cat.informaticassa.icfact.ui.main.pagines;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.service.BuscarEmpresaService;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.BotoSecundari;
import cat.informaticassa.icfact.ui.components.Card;
import cat.informaticassa.icfact.ui.main.components.empresa.EmpresaEvents;
import cat.informaticassa.icfact.ui.main.components.empresa.FormulariEmpresa;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.dirty.DirtyPage;
import cat.informaticassa.icfact.ui.util.dirty.DirtyProvider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PaginaConfiguracio extends VBox implements DirtyProvider {
    private final EmpresaEvents events;

    public PaginaConfiguracio() {
        setSpacing(25);
        setPadding(new Insets(30));
        Label titol = new Label("Configuració");
        titol.setFont(Tema.TITOL);
        Label subtitol = new Label("Modifica les dades de la teva empresa.");
        subtitol.setFont(Tema.SUBTITOL);
        FormulariEmpresa formulari = new FormulariEmpresa();
        Card cardEmpresa = new Card(null, formulari);
        BotoSecundari botoCancelar = new BotoSecundari("❌ Cancel·la");
        BotoPrimari botoGuardar = new BotoPrimari("💾 Desa");
        formulari.setBotoGuardar(botoGuardar);
        HBox barra = new HBox(10);
        Region espai = new Region();
        HBox.setHgrow(espai, Priority.ALWAYS);
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.getChildren().addAll(
                titol,
                espai,
                botoCancelar,
                botoGuardar
        );

        getChildren().addAll(
                barra,
                subtitol,
                cardEmpresa
        );

        Empresa empresa = new BuscarEmpresaService().executar();
        events = new EmpresaEvents(formulari, empresa, botoGuardar, botoCancelar);
    }

    @Override
    public DirtyPage getDirtyPage() {
        return events;
    }
}