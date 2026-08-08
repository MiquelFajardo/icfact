package cat.informaticassa.icfact.ui.main.pagines.factura.table;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class FacturaTable extends TableView {
    private final ObservableList factures = FXCollections.observableArrayList();
    @Setter
    private Consumer<Factura> onModificar;
    @Setter
    private Consumer<Factura> onObrirPdf;
    @Setter
    private Consumer<Factura> onDuplicar;
    @Setter
    private Consumer<Factura> onAnullar;
    @Setter
    private Consumer<Factura> onCobrar;

    public FacturaTable() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        getColumns().addAll(FacturaColumns.numero(), FacturaColumns.data(), FacturaColumns.client(), FacturaColumns.total(), FacturaColumns.estat());
        setItems(factures);
        setPlaceholder(new javafx.scene.control.Label("No hi ha factures."));
        setRowFactory(tv -> {
            TableRow<Factura> row = new TableRow<>();
            ContextMenu menu = new ContextMenu();
            MenuItem obrirPdf = new MenuItem("📄 Obrir PDF");
            MenuItem modificar = new MenuItem("✏ Editar");
            MenuItem duplicar = new MenuItem("📑 Duplicar");
            MenuItem cobrar =  new MenuItem("💰 Cobrar");
            MenuItem anullar = new MenuItem("❌ Anul·lar");
            menu.getItems().addAll(obrirPdf, modificar, duplicar, new SeparatorMenuItem(), cobrar, anullar);
            row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(menu));

            obrirPdf.setOnAction(e -> {
                if (onObrirPdf != null) {
                    onObrirPdf.accept(row.getItem());
                }
            });

            modificar.setOnAction(e -> {
                if (onModificar != null) {
                    onModificar.accept(row.getItem());
                }
            });

            duplicar.setOnAction(e -> {
                if (onDuplicar != null) {
                    onDuplicar.accept(row.getItem());
                }
            });

            cobrar.setOnAction(e -> {
                if (onCobrar != null) {
                    onCobrar.accept(row.getItem());
                }
            });

            anullar.setOnAction(e -> {
                if (onAnullar != null) {
                    onAnullar.accept(row.getItem());
                }
            });

            row.itemProperty().addListener((obs, anterior, factura) -> {
                        if (factura == null) {
                            return;
                        }
                        aplicarEstat(factura, obrirPdf, modificar, duplicar, cobrar, anullar);
                    }
            );

            row.setOnMouseClicked(e -> {
                if (e.getButton() != MouseButton.PRIMARY) {
                    return;
                }
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    if (onModificar != null) {
                        onModificar.accept(row.getItem());
                    }
                }
            });
            return row;
        });
    }

    private void aplicarEstat(Factura factura, MenuItem obrirPdf, MenuItem modificar, MenuItem duplicar, MenuItem cobrar, MenuItem anullar) {
        obrirPdf.setDisable(false);
        modificar.setDisable(false);
        duplicar.setDisable(false);
        cobrar.setDisable(false);
        anullar.setDisable(false);
        EstatFactura estat = factura.getEstat();
        if (estat == null) {
            return;
        }
        switch (estat) {
            case ESBORRANY:
                obrirPdf.setDisable(true);
                cobrar.setDisable(true);
                anullar.setDisable(true);
                break;
            case EMESA:
                break;
            case COBRADA:
                modificar.setDisable(true);
                cobrar.setDisable(true);
                anullar.setDisable(true);
                break;
            case ANULADA:
                cobrar.setDisable(true);
                anullar.setDisable(true);
                break;
        }
    }

    public void mostrar(List<Factura> factures) {
        this.factures.setAll(factures);
    }
}