package cat.informaticassa.icfact.informe.service;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import cat.informaticassa.icfact.informe.service.InformeService.ResumClient;
import cat.informaticassa.icfact.informe.service.InformeService.ResumFacturacio;
import cat.informaticassa.icfact.informe.service.InformeService.ResumIva;
import cat.informaticassa.icfact.pdf.BasePdfPageEvent;
import cat.informaticassa.icfact.pdf.PdfFonts;
import cat.informaticassa.icfact.pdf.PdfUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GenerarPdfInformeService {
    private final EmpresaRepository empresaRepository = new EmpresaRepository();
    private final InformeService informeService = new InformeService();
    private static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Path generarFacturacio(LocalDate desDe, LocalDate finsA) {
        ResumFacturacio resum = informeService.facturacio(desDe, finsA);
        Empresa empresa = obtenirEmpresa();
        Path fitxer = crearFitxer("informe_facturacio", desDe, finsA);

        try {
            Document document = crearDocument(fitxer, empresa);
            afegirCapcalera(document, empresa,"Informe de facturació", desDe, finsA);
            PdfPTable taula = new PdfPTable(2);
            taula.setWidthPercentage(100);
            taula.setWidths(new float[]{65, 35});
            afegirFila(taula,"Factures", String.valueOf( resum.nombreFactures()));
            afegirFila(taula,"Base imposable", PdfUtils.formatImport(resum.subtotal()));
            afegirFila(taula,"IVA", PdfUtils.formatImport(resum.iva()));
            afegirFila(taula, "Total facturat", PdfUtils.formatImport(resum.total()));
            afegirFila(taula,"Total cobrat", PdfUtils.formatImport( resum.cobrat()));
            afegirFila(taula, "Pendent de cobrament", PdfUtils.formatImport(resum.pendent()));
            document.add(taula);
            document.close();
            return fitxer;
        } catch (Exception e) {
            throw new RuntimeException("No s'ha pogut generar l'informe de facturació.", e);
        }
    }

    public Path generarIva(LocalDate desDe, LocalDate finsA) {
        List<ResumIva> registres = informeService.iva(desDe, finsA);
        Empresa empresa = obtenirEmpresa();
        Path fitxer = crearFitxer("informe_iva", desDe, finsA);
        try {
            Document document = crearDocument(fitxer, empresa);
            afegirCapcalera(document, empresa, "Informe d'IVA", desDe, finsA);
            PdfPTable taula = new PdfPTable(4);
            taula.setWidthPercentage(100);
            taula.setWidths(new float[]{20, 30, 25, 25});
            afegirCapcaleraTaula(taula,"IVA");
            afegirCapcaleraTaula(taula,"Base imposable");
            afegirCapcaleraTaula(taula,"Import IVA");
            afegirCapcaleraTaula(taula,"Total");
            for (ResumIva registre : registres) {
                afegirCelda(taula,registre.percentatge().stripTrailingZeros().toPlainString() + " %");
                afegirCelda(taula, PdfUtils.formatImport(registre.base()));
                afegirCelda(taula, PdfUtils.formatImport(registre.importIva()));
                afegirCelda(taula, PdfUtils.formatImport(registre.total()));
            }
            document.add(taula);
            document.close();
            return fitxer;
        } catch (Exception e) {
            throw new RuntimeException("No s'ha pogut generar l'informe d'IVA.", e);
        }
    }

    public Path generarClients(LocalDate desDe, LocalDate finsA) {
        List<ResumClient> registres = informeService.facturacioPerClient(desDe, finsA );
        Empresa empresa = obtenirEmpresa();
        Path fitxer = crearFitxer("informe_facturacio_clients", desDe, finsA);
        try {
            Document document = crearDocument(fitxer, empresa);
            afegirCapcalera(document, empresa,"Facturació per client", desDe, finsA);
            PdfPTable taula = new PdfPTable(4);
            taula.setWidthPercentage(100);
            taula.setWidths(new float[]{40, 20, 20, 20});
            afegirCapcaleraTaula( taula,"Client");
            afegirCapcaleraTaula(taula,"Facturat");
            afegirCapcaleraTaula(taula,"Cobrat");
            afegirCapcaleraTaula(taula,"Pendent");
            for (ResumClient registre : registres) {
                afegirCelda(taula,  registre.client());
                afegirCelda(taula, PdfUtils.formatImport(registre.facturat()));
                afegirCelda(taula, PdfUtils.formatImport(registre.cobrat()));
                afegirCelda(taula, PdfUtils.formatImport( registre.pendent()));
            }
            document.add(taula);
            document.close();
            return fitxer;
        } catch (Exception e) {
            throw new RuntimeException("No s'ha pogut generar l'informe per client.", e);
        }
    }

    private Empresa obtenirEmpresa() {
        return empresaRepository.buscar().orElseThrow(() -> new RuntimeException("No existeix cap empresa."));
    }

    private Document crearDocument(Path fitxer, Empresa empresa) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream( fitxer.toFile()));
            writer.setPageEvent(new BasePdfPageEvent(empresa));
            document.open();
            return document;
        } catch (Exception e) {
            throw new RuntimeException("No s'ha pogut crear el document PDF.", e);
        }
    }

    private void afegirCapcalera(Document document, Empresa empresa,  String titol,  LocalDate desDe, LocalDate finsA) {
        Color color = Color.decode(empresa.getColor());
        Paragraph nomEmpresa = new Paragraph(empresa.getNom(), PdfFonts.nomEmpresa(color));
        nomEmpresa.setSpacingAfter(8);
        document.add(nomEmpresa);
        Paragraph titolInforme = new Paragraph( titol, PdfFonts.titol(color));
        titolInforme.setSpacingAfter(8);
        document.add(titolInforme);
        Paragraph periode = new Paragraph("Període: " + FORMAT_DATA.format(desDe) + " - " + FORMAT_DATA.format(finsA), PdfFonts.normal());
        periode.setSpacingAfter(20);
        document.add(periode);
    }

    private void afegirFila(PdfPTable taula,  String nom, String valor) {
        taula.addCell(new Phrase( nom, PdfFonts.normal()));
        var cellValor = new com.lowagie.text.pdf.PdfPCell(new Phrase(valor, PdfFonts.negreta()));
        cellValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        taula.addCell(cellValor);
    }

    private void afegirCapcaleraTaula(PdfPTable taula, String text) {
        taula.addCell(PdfUtils.crearCapcalera(text, Color.decode( obtenirColorEmpresa())));
    }

    private void afegirCelda(PdfPTable taula, String text) {
        taula.addCell(PdfUtils.crearCelda(text, PdfFonts.normalPetit(), Element.ALIGN_LEFT, Color.WHITE));
    }

    private String obtenirColorEmpresa() {
        return empresaRepository.buscar().map(Empresa::getColor).orElse("#3b82f6");
    }

    private Path crearFitxer(String prefix, LocalDate desDe, LocalDate finsA) {
        Path carpeta = Path.of("informes");
        try {
            Files.createDirectories(carpeta);
        } catch (Exception e) {
            throw new RuntimeException("No s'ha pogut crear la carpeta d'informes.", e );
        }
        return carpeta.resolve(prefix + "_" + desDe + "_" + finsA + ".pdf"
        );
    }
}