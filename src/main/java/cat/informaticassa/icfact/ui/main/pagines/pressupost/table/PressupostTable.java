package cat.informaticassa.icfact.ui.main.pagines.pressupost.table;

import cat.informaticassa.icfact.pressupost.model.Pressupost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Getter
@Setter
public class PressupostTable extends TableView<Pressupost> {
    private final ObservableList<Pressupost> dades = FXCollections.observableArrayList();
    private Consumer<Pressupost> onModificar;
    private Consumer<Pressupost> onObrirPdf;
    private Consumer<Pressupost> onDuplicar;
    private Consumer<Pressupost> onAcceptar;
    private Consumer<Pressupost> onRebutjar;
    private Consumer<Pressupost> onCrearFactura;
    private Consumer<Pressupost> onAfegirPagament;
    private Consumer<Pressupost> onVeurePagaments;

    public PressupostTable() {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        Collections.addAll(
                getColumns(),
                PressupostColumns.columnaNumero(),
                PressupostColumns.columnaData(),
                PressupostColumns.columnaClient(),
                PressupostColumns.columnaEstat(),
                PressupostColumns.columnaFormaPagament(),
                PressupostColumns.columnaTotal()
        );
        setItems(dades);
        setRowFactory(tv -> {
            TableRow<Pressupost> fila = new TableRow<>();
            fila.itemProperty().addListener((obs, anterior, pressupost) -> {
                if (pressupost == null) {
                    fila.setContextMenu(null);
                    return;
                }
                PressupostContextMenu menu = new PressupostContextMenu(pressupost);
                menu.setOnModificar(onModificar);
                menu.setOnObrirPdf(onObrirPdf);
                menu.setOnDuplicar(onDuplicar);
                menu.setOnAcceptar(onAcceptar);
                menu.setOnRebutjar(onRebutjar);
                menu.setOnCrearFactura(onCrearFactura);
                menu.setOnAfegirPagament(onAfegirPagament);
                menu.setOnVeurePagaments(onVeurePagaments);
                fila.setContextMenu(menu);
            });
            fila.setOnMouseClicked(e -> {
                if (e.getButton() != MouseButton.PRIMARY) {
                    return;
                }
                if (e.getClickCount() == 2 && !fila.isEmpty()) {
                    if (onModificar != null) {
                        onModificar.accept(fila.getItem());
                    }
                }
            });
            return fila;
        });
    }

    public void mostrar(List<Pressupost> pressupostos) {
        dades.setAll(pressupostos);
    }
}