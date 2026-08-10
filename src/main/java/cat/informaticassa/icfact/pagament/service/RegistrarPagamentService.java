package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.service.GenerarPdfFacturaService;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import cat.informaticassa.icfact.pdf.service.ObrirPdfService;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class RegistrarPagamentService {
    private final PagamentRepository repository = new PagamentRepository();
    private final ActualitzarEstatPagamentService actualitzarEstatService = new ActualitzarEstatPagamentService();
    private final GenerarPdfFacturaService generarPdfFacturaService = new GenerarPdfFacturaService();
    private final ObrirPdfService obrirPdfService = new ObrirPdfService();

    public Path executar(Pagament pagament) {
        validar(pagament);
        LocalDateTime ara = LocalDateTime.now();
        pagament.setDataCreacio(ara);
        pagament.setDataModificacio(ara);
        pagament.setActiu(true);
        repository.guardar(pagament);
        actualitzarEstatService.executar(pagament);
        if (pagament.getFactura() != null) {
            Factura factura = pagament.getFactura();
            return generarPdfFacturaService.executar(factura.getId());
        }
        return null;
    }

    public void obrirPdf(Path pdf) {
        if (pdf != null) {
            obrirPdfService.executar(pdf);
        }
    }

    private void validar(Pagament pagament) {
        if (pagament.getPressupost() == null && pagament.getFactura() == null) {
            throw new IllegalArgumentException("El pagament ha d'estar relacionat amb un pressupost o una factura.");
        }

        if (pagament.getImportPagat() == null || pagament.getImportPagat().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("L'import del pagament ha de ser superior a zero.");
        }

        if (pagament.getDataPagament() == null) {
            throw new IllegalArgumentException("La data del pagament és obligatòria.");
        }
    }
}