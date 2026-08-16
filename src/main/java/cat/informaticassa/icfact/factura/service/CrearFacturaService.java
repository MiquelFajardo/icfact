package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.infraestructura.model.TipusDocument;
import cat.informaticassa.icfact.infraestructura.service.GenerarNumeroDocumentService;
import cat.informaticassa.icfact.infraestructura.service.ObtenirSeguentNumeroDocumentService;
import java.time.LocalDateTime;
import java.time.Year;

public class CrearFacturaService {
    private static final String PREFIX_NUMERO = "F";
    private final FacturaRepository repository = new FacturaRepository();
    private final ObtenirSeguentNumeroDocumentService numeroService = new ObtenirSeguentNumeroDocumentService();
    private final ValidarFacturaService validarService = new ValidarFacturaService();
    private final RecalcularFacturaService recalcularService = new RecalcularFacturaService();

    public Factura executar(Factura factura) {
        validarService.executar(factura);
        recalcularService.executar(factura);
        factura.setNumero(generarNumero());
        factura.setActiu(true);
        factura.setDataCreacio(LocalDateTime.now());
        factura.setDataModificacio(LocalDateTime.now());
        for (LiniaFactura linia : factura.getLinies()) {
            linia.setFactura(factura);
            linia.setActiu(true);
        }
        repository.guardar(factura);
        return factura;
    }

    public String generarNumero() {
        long numero = numeroService.obtenir(Year.now().getValue(), TipusDocument.FACTURA);
        return GenerarNumeroDocumentService.generar(PREFIX_NUMERO, numero);
    }
}