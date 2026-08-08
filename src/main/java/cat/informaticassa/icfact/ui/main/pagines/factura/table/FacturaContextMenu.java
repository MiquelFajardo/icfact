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
    private Consumer<Factura> onCobrar;
    private Consumer<Factura> onAnullar;

    public FacturaContextMenu(Factura factura) {
        MenuItem obrirPdf = new MenuItem("📄 Obrir PDF");
        MenuItem modificar = new MenuItem("✏ Editar");
        MenuItem duplicar = new MenuItem("📑 Duplicar");
        MenuItem cobrar = new MenuItem("💰 Cobrar");
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

        cobrar.setOnAction(e -> {
            if (onCobrar != null) {
                onCobrar.accept(factura);
            }
        });

        anullar.setOnAction(e -> {
            if (onAnullar != null) {
                onAnullar.accept(factura);
            }
        });

        getItems().addAll(
                obrirPdf,
                modificar,
                duplicar,
                new SeparatorMenuItem(),
                cobrar,
                anullar
        );

        switch (factura.getEstat()) {
            case ESBORRANY -> {
                obrirPdf.setDisable(true);
                cobrar.setDisable(true);
                anullar.setDisable(true);
            }

            case EMESA -> { }

            case COBRADA -> {
                modificar.setDisable(true);
                cobrar.setDisable(true);
                anullar.setDisable(true);
            }

            case ANULADA -> {
                cobrar.setDisable(true);
                anullar.setDisable(true);
            }
        }

        if (!factura.isActiu()) {
            modificar.setDisable(true);
            duplicar.setDisable(true);
            cobrar.setDisable(true);
            anullar.setDisable(true);
        }
    }
}