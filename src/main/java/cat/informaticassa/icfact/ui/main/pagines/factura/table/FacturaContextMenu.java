package cat.informaticassa.icfact.ui.main.pagines.factura.table;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
public class FacturaContextMenu extends ContextMenu {
    private Consumer<Factura> onModificar;
    private Consumer<Factura> onObrirPdf;
    private Consumer<Factura> onDuplicar;
    private Consumer<Factura> onAfegirPagament;
    private Consumer<Factura> onVeurePagaments;
    private Consumer<Factura> onAnullar;

    public FacturaContextMenu(Factura factura) {
        MenuItem obrirPdf = new MenuItem("📄 Obrir PDF");
        MenuItem modificar = new MenuItem("✏ Editar");
        MenuItem duplicar = new MenuItem("📑 Duplicar");
        MenuItem afegirPagament = new MenuItem("💶 Afegir pagament");
        MenuItem veurePagaments = new MenuItem("💳 Veure pagaments");
        MenuItem anullar = new MenuItem("❌ Anul·lar");

        obrirPdf.setOnAction(e -> {
            if (onObrirPdf != null) {
                onObrirPdf.accept(factura);
            }
        });

        modificar.setOnAction(e -> {
            if (onModificar != null) {
                onModificar.accept(factura);
            }
        });

        duplicar.setOnAction(e -> {
            if (onDuplicar != null) {
                onDuplicar.accept(factura);
            }
        });

        afegirPagament.setOnAction(e -> {
            if (onAfegirPagament != null) {
                onAfegirPagament.accept(factura);
            }
        });

        veurePagaments.setOnAction(e -> {
            if (onVeurePagaments != null) {
                onVeurePagaments.accept(factura);
            }
        });

        anullar.setOnAction(e -> {
            if (onAnullar != null) {
                onAnullar.accept(factura);
            }
        });

        getItems().addAll(obrirPdf, modificar, duplicar, afegirPagament, veurePagaments, new SeparatorMenuItem(), anullar);

        switch (factura.getEstat()) {
            case ESBORRANY -> {
                obrirPdf.setDisable(true);
                afegirPagament.setDisable(true);
                veurePagaments.setDisable(true);
                anullar.setDisable(true);
            }
            case EMESA -> {
                anullar.setDisable(factura.getPagaments() != null && !factura.getPagaments().isEmpty());
            }
            case COBRADA -> {
                modificar.setDisable(true);
                afegirPagament.setDisable(true);
                anullar.setDisable(true);
                veurePagaments.setDisable(factura.getPagaments() == null || factura.getPagaments().isEmpty());
            }
            case ANULADA -> {
                afegirPagament.setDisable(true);
                anullar.setDisable(true);
                veurePagaments.setDisable(factura.getPagaments() == null || factura.getPagaments().isEmpty());
            }
        }

        if (!factura.isActiu()) {
            modificar.setDisable(true);
            duplicar.setDisable(true);
            afegirPagament.setDisable(true);
            anullar.setDisable(true);
        }
    }
}