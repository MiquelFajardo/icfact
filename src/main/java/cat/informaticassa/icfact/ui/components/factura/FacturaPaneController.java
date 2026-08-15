package cat.informaticassa.icfact.ui.components.factura;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.service.BuscarClientsService;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.service.BuscarFormesPagamentService;
import cat.informaticassa.icfact.ui.components.dialogs.ClientDialog;
import cat.informaticassa.icfact.ui.components.dialogs.FormaPagamentDialog;
import cat.informaticassa.icfact.ui.main.pagines.factura.FacturaPane;

public class FacturaPaneController {
    private final FacturaPane pane;
    private final BuscarClientsService buscarClientsService = new BuscarClientsService();
    private final BuscarFormesPagamentService buscarFormesPagamentService = new BuscarFormesPagamentService();

    public FacturaPaneController(FacturaPane pane) {
        this.pane = pane;
        inicialitzar();
    }

    private void inicialitzar() {
        pane.getTxtClient().setDisplayFunction(Client::getNom);
        pane.getTxtClient().setSearchFunction(client ->
                client.getNom()
                        + " "
                        + client.getNif()
                        + " "
                        + client.getTelefon()
        );

        pane.getCmbFormaPagament().setConverter(
                new javafx.util.StringConverter<>() {
                    @Override
                    public String toString(FormaPagament fp) {
                        return fp == null ? "" : fp.getNom();
                    }
                    @Override
                    public FormaPagament fromString(String string) {
                        return null;
                    }
                }
        );

        pane.getLblEstat().setText("ESBORRANY");
        pane.getDpData().setValue(java.time.LocalDate.now());
        pane.getBotoNouClient().setOnAction(e -> nouClient());
        pane.getBotoNovaFormaPagament().setOnAction(e -> novaFormaPagament());
        carregarClients();
        carregarFormesPagament();
    }

    private void carregarClients() {
        pane.getTxtClient().setItems(buscarClientsService.buscarTots());
    }

    private void carregarFormesPagament() {
        pane.getCmbFormaPagament().getItems().setAll(buscarFormesPagamentService.buscarActius());
    }

    private void nouClient() {
        ClientDialog dialog = new ClientDialog();
        dialog.initOwner(pane.getScene().getWindow());
        dialog.showAndWait();
        if (dialog.getClient() != null) {
            carregarClients();
            pane.getTxtClient().setSelectedItem(dialog.getClient());
        }
    }

    private void novaFormaPagament() {
        FormaPagamentDialog dialog = new FormaPagamentDialog();
        dialog.initOwner(pane.getScene().getWindow());
        dialog.showAndWait();
        if (dialog.getFormaPagament() != null) {
            carregarFormesPagament();
            pane.getCmbFormaPagament().setValue(dialog.getFormaPagament());
        }
    }
}