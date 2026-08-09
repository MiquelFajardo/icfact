package cat.informaticassa.icfact.ui.main.pagines.factura.table;

import cat.informaticassa.icfact.factura.model.Factura;
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
@Setter
public class FacturaTable extends TableView {
    private final ObservableList factures = FXCollections.observableArrayList();
    private Consumer<Factura> onModificar;
    private Consumer<Factura> onObrirPdf;
    private Consumer<Factura> onDuplicar;
    private Consumer<Factura> onAnullar;
    private Consumer<Factura> onAfegirPagament;
    private Consumer<Factura> onVeurePagaments;

    public FacturaTable() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        getColumns().addAll(FacturaColumns.numero(), FacturaColumns.data(), FacturaColumns.client(), FacturaColumns.total(), FacturaColumns.estat());
        setItems(factures);
        setPlaceholder( new javafx.scene.control.Label("No hi ha factures."));
        setRowFactory(tv -> {
            TableRow<Factura> row = new TableRow<>();
            ContextMenu menu = new ContextMenu();
            MenuItem obrirPdf = new MenuItem("📄 Obrir PDF");
            MenuItem modificar = new MenuItem("✏ Editar");
            MenuItem duplicar = new MenuItem("📑 Duplicar");
            MenuItem afegirPagament = new MenuItem("💶 Afegir pagament");
            MenuItem veurePagaments = new MenuItem("💳 Veure pagaments");
            MenuItem anullar = new MenuItem("❌ Anul·lar");
            menu.getItems().addAll(obrirPdf, modificar, duplicar, afegirPagament, veurePagaments, new SeparatorMenuItem(), anullar);

            menu.setOnShowing(e -> {
                Factura factura = row.getItem();
                if (factura == null) {
                    return;
                }
                obrirPdf.setOnAction(ev -> {
                    if (onObrirPdf != null) {
                        onObrirPdf.accept(factura);
                    }
                });

                modificar.setOnAction(ev -> {
                    if (onModificar != null) {
                        onModificar.accept(factura);
                    }
                });

                duplicar.setOnAction(ev -> {
                    if (onDuplicar != null) {
                        onDuplicar.accept(factura);
                    }
                });

                afegirPagament.setOnAction(ev -> {
                    if (onAfegirPagament != null) {
                        onAfegirPagament.accept(factura);
                    }
                });

                veurePagaments.setOnAction(ev -> {
                    if (onVeurePagaments != null) {
                        onVeurePagaments.accept(factura);
                    }
                });

                anullar.setOnAction(ev -> {
                    if (onAnullar != null) {
                        onAnullar.accept(factura);
                    }
                });
            });

            menu.setOnShowing(e -> {
                Factura factura = row.getItem();
                if (factura == null) {
                    return;
                }

                obrirPdf.setOnAction(ev -> {
                    if (onObrirPdf != null) {
                        onObrirPdf.accept(factura);
                    }
                });

                modificar.setOnAction(ev -> {
                    if (onModificar != null) {
                        onModificar.accept(factura);
                    }
                });

                duplicar.setOnAction(ev -> {
                    if (onDuplicar != null) {
                        onDuplicar.accept(factura);
                    }
                });

                afegirPagament.setOnAction(ev -> {
                    if (onAfegirPagament != null) {
                        onAfegirPagament.accept(factura);
                    }
                });

                veurePagaments.setOnAction(ev -> {
                    if (onVeurePagaments != null) {
                        onVeurePagaments.accept(factura);
                    }
                });

                anullar.setOnAction(ev -> {
                    if (onAnullar != null) {
                        onAnullar.accept(factura);
                    }
                });

                switch (factura.getEstat()) {

                    case ESBORRANY -> {
                        obrirPdf.setDisable(true);
                        afegirPagament.setDisable(true);
                        veurePagaments.setDisable(true);
                        anullar.setDisable(true);
                    }

                    case EMESA -> {
                        obrirPdf.setDisable(false);
                        modificar.setDisable(false);
                        duplicar.setDisable(false);
                        afegirPagament.setDisable(false);

                        boolean tePagaments =
                                factura.getPagaments() != null
                                        && !factura.getPagaments().isEmpty();

                        veurePagaments.setDisable(!tePagaments);
                        anullar.setDisable(tePagaments);
                    }

                    case COBRADA -> {
                        obrirPdf.setDisable(false);
                        modificar.setDisable(true);
                        duplicar.setDisable(false);
                        afegirPagament.setDisable(true);

                        boolean tePagaments =
                                factura.getPagaments() != null
                                        && !factura.getPagaments().isEmpty();

                        veurePagaments.setDisable(!tePagaments);
                        anullar.setDisable(true);
                    }

                    case ANULADA -> {
                        obrirPdf.setDisable(false);
                        modificar.setDisable(false);
                        duplicar.setDisable(false);
                        afegirPagament.setDisable(true);
                        anullar.setDisable(true);

                        boolean tePagaments =
                                factura.getPagaments() != null
                                        && !factura.getPagaments().isEmpty();

                        veurePagaments.setDisable(!tePagaments);
                    }
                }

                if (!factura.isActiu()) {
                    modificar.setDisable(true);
                    duplicar.setDisable(true);
                    afegirPagament.setDisable(true);
                    anullar.setDisable(true);
                }
            });



            row.contextMenuProperty().bind(javafx.beans.binding.Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(menu));
            row.setOnMouseClicked(e -> {
                if (e.getButton() != MouseButton.PRIMARY) {
                    return;
                }
                if (e.getClickCount() == 2
                        && !row.isEmpty()) {
                    if (onModificar != null) {
                        onModificar.accept(row.getItem());
                    }
                }
            });
            return row;
        });
    }

    public void mostrar(List<Factura> factures) {
        this.factures.setAll(factures);
    }
}