package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import cat.informaticassa.icfact.pdf.PdfObservacions;
import cat.informaticassa.icfact.pdf.pressupost.*;
import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import cat.informaticassa.icfact.pdf.BasePdfPageEvent;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class GenerarPdfPressupostService {

    private final PressupostRepository pressupostRepository = new PressupostRepository();
    private final EmpresaRepository empresaRepository = new EmpresaRepository();
    private final PdfCapcaleraPressupost pdfCapcalera = new PdfCapcaleraPressupost();
    private final PdfLiniesPressupost pdfLinies = new PdfLiniesPressupost();
    private final PdfTotalsPressupost pdfTotals = new PdfTotalsPressupost();
    private final PdfFormaPagamentPressupost pdfFormaPagament = new PdfFormaPagamentPressupost();
    private final PdfAcceptacioPressupost pdfAcceptacio = new PdfAcceptacioPressupost();
    private final PdfObservacions pdfObservacions = new PdfObservacions();

    public Path executar(Pressupost pressupost) {
        pressupost = obtenirPressupost(pressupost.getId());
        Empresa empresa = obtenirEmpresa();
        Path fitxer = crearFitxer(pressupost);
        try {
            Document document = crearDocument(fitxer, empresa, pressupost);
            generarContingut(document, pressupost, empresa);
            document.close();
            return fitxer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Empresa obtenirEmpresa() {
        return empresaRepository.buscar().orElseThrow(() -> new RuntimeException("No existeix cap empresa."));
    }

    private Pressupost obtenirPressupost(Long pressupostId) {
        return pressupostRepository.buscarPerIdAmbLinies(pressupostId).orElseThrow(() -> new PressupostNoExisteixException("El pressupost no existeix."));
    }

    private Path crearFitxer(Pressupost pressupost) {
        Path carpeta = Path.of("pressupostos");
        try {
            Files.createDirectories(carpeta);
        } catch (IOException e) {
            throw new RuntimeException("No s'ha pogut crear la carpeta de pressupostos.", e);
        }
        return carpeta.resolve(pressupost.getNumero() + ".pdf");
    }

    private Document crearDocument(Path fitxer, Empresa empresa, Pressupost pressupost) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fitxer.toFile()));
            writer.setPageEvent(new BasePdfPageEvent(empresa));
            document.open();
            return document;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generarContingut(Document document, Pressupost pressupost, Empresa empresa) {
        pdfCapcalera.afegir(document, pressupost, empresa);
        pdfLinies.afegir(document, pressupost, empresa);
        pdfTotals.afegir(document, pressupost, empresa);
        pdfFormaPagament.afegir(document, pressupost, empresa);
        pdfObservacions.afegir(document, pressupost.getObservacions(), empresa);
        pdfAcceptacio.afegir(document, empresa);
    }

}