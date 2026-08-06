package cat.informaticassa.icfact.ui.main.pagines.pressupost.table;

import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
public class PressupostContextMenu extends ContextMenu {

    private Consumer<Pressupost> onModificar;
    private Consumer<Pressupost> onObrirPdf;
    private Consumer<Pressupost> onDuplicar;
    private Consumer<Pressupost> onAcceptar;
    private Consumer<Pressupost> onRebutjar;
    private Consumer<Pressupost> onCrearFactura;

    public PressupostContextMenu(Pressupost pressupost) {

        MenuItem obrirPdf = new MenuItem("📄 Obrir PDF");
        MenuItem modificar = new MenuItem("✏ Editar");
        MenuItem duplicar = new MenuItem("📑 Duplicar");
        MenuItem acceptar = new MenuItem("✔ Acceptar");
        MenuItem rebutjar = new MenuItem("❌ Rebutjar");
        MenuItem crearFactura = new MenuItem("🧾 Crear factura");

        obrirPdf.setOnAction(e -> {
            if (onObrirPdf != null) {
                onObrirPdf.accept(pressupost);
            }
        });

        modificar.setOnAction(e -> {
            if (onModificar != null) {
                onModificar.accept(pressupost);
            }
        });

        duplicar.setOnAction(e -> {
            if (onDuplicar != null) {
                onDuplicar.accept(pressupost);
            }
        });

        acceptar.setOnAction(e -> {
            if (onAcceptar != null) {
                onAcceptar.accept(pressupost);
            }
        });

        rebutjar.setOnAction(e -> {
            if (onRebutjar != null) {
                onRebutjar.accept(pressupost);
            }
        });

        crearFactura.setOnAction(e -> {
            if (onCrearFactura != null) {
                onCrearFactura.accept(pressupost);
            }
        });

        getItems().addAll(
                obrirPdf,
                modificar,
                duplicar
        );

        if (pressupost.isActiu()) {
            getItems().add(new SeparatorMenuItem());
            getItems().addAll(
                    acceptar,
                    rebutjar
            );

            if (pressupost.getEstat() == EstatPressupost.ACCEPTAT) {
                getItems().add(new SeparatorMenuItem());
                getItems().add(crearFactura);
            }
        }

        switch (pressupost.getEstat()) {
            case ESBORRANY -> {
                obrirPdf.setDisable(true);
                acceptar.setDisable(true);
                rebutjar.setDisable(true);
            }

            case ENVIAT -> {
            }

            case ACCEPTAT -> {
                acceptar.setDisable(true);
            }

            case REBUTJAT -> {
                rebutjar.setDisable(true);
            }

            case FACTURAT -> {
                modificar.setDisable(true);
                acceptar.setDisable(true);
                rebutjar.setDisable(true);
            }
        }
    }
}