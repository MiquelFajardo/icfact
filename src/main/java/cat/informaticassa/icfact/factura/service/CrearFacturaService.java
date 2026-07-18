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

    private final FacturaRepository repository = new FacturaRepository();
    private final ObtenirSeguentNumeroDocumentService numeroService = new ObtenirSeguentNumeroDocumentService();
    private final ValidarFacturaService validarService = new ValidarFacturaService();
    private final RecalcularFacturaService recalcularService = new RecalcularFacturaService();

    public Factura executar(Factura factura) {
        validarService.executar(factura);
        recalcularService.executar(factura);

        long numero = numeroService.obtenir(Year.now().getValue(), TipusDocument.FACTURA);

        factura.setNumero(GenerarNumeroDocumentService.generar("F", numero));
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
}