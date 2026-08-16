package cat.informaticassa.icfact.ui.main.pagines.informe;

import cat.informaticassa.icfact.informe.service.InformeService;
import cat.informaticassa.icfact.informe.service.InformeService.ResumClient;
import cat.informaticassa.icfact.informe.service.InformeService.ResumFacturacio;
import cat.informaticassa.icfact.informe.service.InformeService.ResumIva;
import cat.informaticassa.icfact.ui.components.BotoPrimari;
import cat.informaticassa.icfact.ui.components.Card;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.Alerta;
import cat.informaticassa.icfact.informe.service.GenerarPdfInformeService;
import cat.informaticassa.icfact.pdf.service.ObrirPdfService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Locale;

@SuppressWarnings("unchecked")
public class PaginaInformes extends BorderPane {
    private final InformeService informeService = new InformeService();
    private final DatePicker dateDesDe = new DatePicker();
    private final DatePicker dateFinsA =   new DatePicker();
    private final BotoPrimari botoAplicar = new BotoPrimari("Aplicar");
    private final VBox panellFacturacio = new VBox(20);
    private final VBox panellIva = new VBox(20);
    private final VBox panellClients = new VBox(20);
    private final GenerarPdfInformeService generarPdfService = new GenerarPdfInformeService();
    private final ObrirPdfService obrirPdfService = new ObrirPdfService();
    private final BotoPrimari botoPdfFacturacio = new BotoPrimari("📄 Generar PDF");
    private final BotoPrimari botoPdfIva = new BotoPrimari("📄 Generar PDF");
    private final BotoPrimari botoPdfClients = new BotoPrimari("📄 Generar PDF");

    public PaginaInformes() {
        setPadding(new Insets(30));
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        dateFinsA.setValue(LocalDate.now());
        dateDesDe.setValue(LocalDate.now().minusDays(29));
        Label titol = new Label("Informes");
        titol.setFont(Tema.TITOL);
        Label subtitol = new Label("Consulta la facturació, l'IVA i la facturació per client.");
        subtitol.setFont(Tema.SUBTITOL);
        VBox capcalera = new VBox(5, titol, subtitol);
        Card cardFiltres = crearCardFiltres();
        Tab tabFacturacio = new Tab("Facturació", panellFacturacio);
        Tab tabIva = new Tab("IVA", panellIva);
        Tab tabClients = new Tab("Facturació per client", panellClients);
        tabFacturacio.setClosable(false);
        tabIva.setClosable(false);
        tabClients.setClosable(false);

        TabPane pestanyes = new TabPane();
        pestanyes.getTabs().addAll(tabFacturacio, tabIva, tabClients);
        pestanyes.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        botoAplicar.setOnAction(e -> carregar());

        botoPdfFacturacio.setOnAction(e -> generarPdfFacturacio());
        botoPdfIva.setOnAction(e -> generarPdfIva());
        botoPdfClients.setOnAction(e -> generarPdfClients());

        VBox contingut = new VBox(20, capcalera, cardFiltres, pestanyes);
        VBox.setVgrow(pestanyes, Priority.ALWAYS);
        setCenter(contingut);
        carregar();
    }

    public void carregar() {
        LocalDate desDe = dateDesDe.getValue();
        LocalDate finsA = dateFinsA.getValue();
        if (desDe == null || finsA == null) {
            return;
        }
        if (desDe.isAfter(finsA)) {
            Alerta.error(this.getScene().getWindow(),"La data inicial no pot ser posterior a la data final.");
            return;
        }
        carregarFacturacio(desDe, finsA);
        carregarIva(desDe, finsA);
        carregarClients(desDe, finsA);
    }


    private Card crearCardFiltres() {
        Label lblDesDe = new Label("Des de");
        Label lblFinsA = new Label("Fins a");
        VBox grupDesDe = new VBox(5, lblDesDe, dateDesDe);
        VBox grupFinsA = new VBox(5, lblFinsA, dateFinsA);
        HBox fila = new HBox(20, grupDesDe, grupFinsA, botoAplicar);
        fila.setAlignment(Pos.BOTTOM_LEFT);
        return new Card("Període", fila);
    }

    private void carregarFacturacio(LocalDate desDe, LocalDate finsA) {
        panellFacturacio.getChildren().clear();
        ResumFacturacio resum = informeService.facturacio(desDe, finsA);
        HBox targetes = new HBox(15);
        targetes.getChildren().addAll(crearTargeta("FACTURES", String.valueOf(resum.nombreFactures())),
                crearTargeta("TOTAL FACTURAT", euros(resum.total())),
                crearTargeta("TOTAL COBRAT", euros(resum.cobrat())),
                crearTargeta("PENDENT", euros(resum.pendent())));
        GridPane resumGraella = crearGraella();
        afegirFila(resumGraella, 0, "Base imposable", euros(resum.subtotal()));
        afegirFila(resumGraella, 1, "IVA",euros(resum.iva()));
        afegirFila(resumGraella, 2, "Total facturat", euros(resum.total()));
        afegirFila(resumGraella, 3, "Total cobrat", euros(resum.cobrat()));
        afegirFila(resumGraella, 4, "Pendent de cobrament", euros(resum.pendent()));
        Card cardResum = new Card("Resum", resumGraella);
        HBox boto = new HBox(botoPdfFacturacio);
        boto.setAlignment(Pos.CENTER_RIGHT);
        panellFacturacio.getChildren().addAll(targetes, cardResum, boto);
    }

    private VBox crearTargeta(String titol, String valor) {
        Label lblTitol = new Label(titol);
        lblTitol.setStyle("-fx-font-size: 12px;fx-font-weight: bold;");
        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox targeta = new VBox(8, lblTitol, lblValor);
        targeta.setPadding(new Insets(18));
        targeta.setAlignment(Pos.CENTER_LEFT);
        targeta.setMinWidth(170);
        targeta.setPrefWidth(190);
        targeta.setStyle("-fx-background-color: white; -fx-border-color: #dddddd; -fx-border-radius: 6; -fx-background-radius: 6;");
        return targeta;
    }

    private void carregarIva(LocalDate desDe, LocalDate finsA) {
        panellIva.getChildren().clear();
        TableView<ResumIva> taula = new TableView<>();
        taula.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        TableColumn<ResumIva, String> colIva = new TableColumn<>("IVA");
        colIva.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(formatPercentatge(data.getValue().percentatge())));
        TableColumn<ResumIva, String> colBase = new TableColumn<>("Base imposable");
        colBase.setCellValueFactory( data ->
                        new javafx.beans.property.SimpleStringProperty(euros(data.getValue().base())));
        TableColumn<ResumIva, String> colImport = new TableColumn<>("IVA");
        colImport.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(euros(data.getValue().importIva())));
        TableColumn<ResumIva, String> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(euros(data.getValue().total())));
        taula.getColumns().addAll(colIva, colBase, colImport, colTotal);
        taula.getItems().setAll(informeService.iva(desDe, finsA));
        VBox.setVgrow(taula, Priority.ALWAYS);
        Card card = new Card("Resum d'IVA", taula);
        VBox.setVgrow(card, Priority.ALWAYS);
        HBox boto = new HBox(botoPdfIva);
        boto.setAlignment(Pos.CENTER_RIGHT);
        panellIva.getChildren().addAll(card, boto);
    }

    private void carregarClients(LocalDate desDe, LocalDate finsA) {
        panellClients.getChildren().clear();
        TableView<ResumClient> taula = new TableView<>();
        taula.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        TableColumn<ResumClient, String> colClient = new TableColumn<>("Client");
        colClient.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(data.getValue().client()));
        TableColumn<ResumClient, String> colFacturat = new TableColumn<>("Facturat");
        colFacturat.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(euros(data.getValue().facturat())));
        TableColumn<ResumClient, String> colCobrat = new TableColumn<>("Cobrat");
        colCobrat.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(euros(data.getValue().cobrat())));
        TableColumn<ResumClient, String> colPendent = new TableColumn<>("Pendent");
        colPendent.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(euros(data.getValue().pendent())));
        taula.getColumns().addAll(colClient, colFacturat, colCobrat, colPendent);
        taula.getItems().setAll(informeService.facturacioPerClient(desDe, finsA));
        VBox.setVgrow(taula, Priority.ALWAYS);
        Card card = new Card("Facturació per client", taula);
        VBox.setVgrow(card, Priority.ALWAYS);
        HBox boto = new HBox(botoPdfClients);
        boto.setAlignment(Pos.CENTER_RIGHT);
        panellClients.getChildren().addAll(card, boto);
    }

    private GridPane crearGraella() {
        GridPane graella = new GridPane();
        graella.setHgap(40);
        graella.setVgap(15);
        graella.setPadding(new Insets(5));
        return graella;
    }

    private void afegirFila(GridPane graella, int fila, String nom, String valor) {
        Label lblNom = new Label(nom);
        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-weight: bold;");
        graella.add(lblNom,0, fila);
        graella.add(lblValor, 1, fila);
    }

    private String euros(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("ca-ES"),"%,.2f €", valor);
    }

    private String formatPercentatge(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("ca-ES"),"%.0f %%", valor);
    }

    private void generarPdfFacturacio() {
        try {
            Path pdf = generarPdfService.generarFacturacio(dateDesDe.getValue(), dateFinsA.getValue());
            obrirPdfService.executar(pdf);
        } catch (Exception ex) {
            Alerta.error(getScene().getWindow(), ex.getMessage());
        }
    }

    private void generarPdfIva() {
        try {
            Path pdf = generarPdfService.generarIva(dateDesDe.getValue(), dateFinsA.getValue());
            obrirPdfService.executar(pdf);
        } catch (Exception ex) {
            Alerta.error(getScene().getWindow(), ex.getMessage());
        }
    }

    private void generarPdfClients() {
        try {
            Path pdf = generarPdfService.generarClients(dateDesDe.getValue(), dateFinsA.getValue());
            obrirPdfService.executar(pdf);
        } catch (Exception ex) {
            Alerta.error(getScene().getWindow(), ex.getMessage());
        }
    }
}