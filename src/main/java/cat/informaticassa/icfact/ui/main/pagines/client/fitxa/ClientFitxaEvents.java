package cat.informaticassa.icfact.ui.main.pagines.client.fitxa;

public class ClientFitxaEvents {

    private final ClientFitxaPage pagina;

    public ClientFitxaEvents(ClientFitxaPage pagina) {

        this.pagina = pagina;

        inicialitzar();
    }

    private void inicialitzar() {

        pagina.getHeader().getBotoEditar().setOnAction(e -> editar());

        pagina.getHeader().getBotoGuardar().setOnAction(e -> guardar());

        pagina.getHeader().getBotoCancelar().setOnAction(e -> cancelar());
    }

    private void editar() {

        pagina.getHeader().mostrarModeEdicio();
        pagina.getClientCard().mostrarModeEdicio();
    }

    private void guardar() {

        // TODO guardar client

        pagina.getHeader().mostrarModeConsulta();
        pagina.getClientCard().mostrarModeConsulta();
    }

    private void cancelar() {

        // TODO recarregar client

        pagina.getHeader().mostrarModeConsulta();
        pagina.getClientCard().mostrarModeConsulta();
    }
}