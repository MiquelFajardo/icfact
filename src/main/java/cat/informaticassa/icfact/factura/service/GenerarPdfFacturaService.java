package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import cat.informaticassa.icfact.factura.exception.FacturaNoExisteixException;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import cat.informaticassa.icfact.pdf.BasePdfPageEvent;
import cat.informaticassa.icfact.pdf.MarcaAiguaPdf;
import cat.informaticassa.icfact.pdf.PdfObservacions;
import cat.informaticassa.icfact.pdf.factura.PdfCapcaleraFactura;
import cat.informaticassa.icfact.pdf.factura.PdfFormaPagamentFactura;
import cat.informaticassa.icfact.pdf.factura.PdfLiniesFactura;
import cat.informaticassa.icfact.pdf.factura.PdfTotalsFactura;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenerarPdfFacturaService {

    private final FacturaRepository repository = new FacturaRepository();
    private final EmpresaRepository empresaRepository = new EmpresaRepository();

    private final PdfCapcaleraFactura pdfCapcalera = new PdfCapcaleraFactura();
    private final PdfLiniesFactura pdfLinies = new PdfLiniesFactura();
    private final PdfTotalsFactura pdfTotals = new PdfTotalsFactura();
    private final PdfFormaPagamentFactura pdfFormaPagament = new PdfFormaPagamentFactura();
    private final PagamentRepository pagamentRepository = new PagamentRepository();
    private final MarcaAiguaPdf marcaAiguaPdf = new MarcaAiguaPdf();
    private final PdfObservacions pdfObservacions = new PdfObservacions();

    private PdfWriter writer;


    public Path executar(Long facturaId) {
        Empresa empresa = obtenirEmpresa();
        Factura factura = obtenirFactura(facturaId);
        var importPagat = pagamentRepository.calcularImportPagat(factura);
        Path fitxer = crearFitxer(factura);

        try {
            Document document = crearDocument(fitxer, empresa);
            generarContingut(document, factura, importPagat, empresa);
            document.close();
            return fitxer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Empresa obtenirEmpresa() {
        return empresaRepository.buscar().orElseThrow(() -> new RuntimeException("No existeix cap empresa."));
    }

    private Factura obtenirFactura(Long facturaId) {
        return repository.buscarPerIdAmbLinies(facturaId).orElseThrow(() -> new FacturaNoExisteixException("La factura no existeix."));
    }

    private Path crearFitxer(Factura factura) {
        Path carpeta = Path.of("factures");
        try {
            Files.createDirectories(carpeta);
        } catch (IOException e) {
            throw new RuntimeException("No s'ha pogut crear la carpeta de factures.", e);
        }
        return carpeta.resolve(factura.getNumero() + ".pdf");
    }

    private Document crearDocument(Path fitxer, Empresa empresa) {
        try {
            Document document = new Document(PageSize.A4);
            writer = PdfWriter.getInstance(document, new FileOutputStream(fitxer.toFile()));
            writer.setPageEvent(new BasePdfPageEvent(empresa) );
            document.open();
            return document;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generarContingut(Document document, Factura factura, BigDecimal importPagat ,Empresa empresa) {
        pdfCapcalera.afegir(document, factura, empresa);
        pdfLinies.afegir(document, factura, empresa);
        pdfTotals.afegir(document, factura, importPagat, empresa);
        pdfFormaPagament.afegir(document, factura, empresa);
        pdfObservacions.afegir(document, factura.getObservacions(), empresa);
        marcaAiguaPdf.dibuixar(writer, factura.getEstat().name());

    }
}