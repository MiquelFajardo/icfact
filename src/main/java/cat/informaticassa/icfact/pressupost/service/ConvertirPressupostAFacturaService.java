package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.infraestructura.model.TipusDocument;
import cat.informaticassa.icfact.infraestructura.service.GenerarNumeroDocumentService;
import cat.informaticassa.icfact.infraestructura.service.ObtenirSeguentNumeroDocumentService;
import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.time.LocalDate;
import java.time.Year;

public class ConvertirPressupostAFacturaService {
    private final PressupostRepository repository = new PressupostRepository();
    private final ObtenirSeguentNumeroDocumentService numeroService = new ObtenirSeguentNumeroDocumentService();

    public Factura preparar(Long pressupostId) {
        Pressupost pressupost = repository.buscarPerIdAmbLinies(pressupostId).orElseThrow(() -> new PressupostNoExisteixException("El pressupost no existeix."));
        Factura factura = new Factura();
        long numero = numeroService.obtenir(Year.now().getValue(), TipusDocument.FACTURA);
        factura.setNumero(GenerarNumeroDocumentService.generar("F", numero));
        factura.setPressupost(pressupost);
        factura.setData(LocalDate.now());
        factura.setEstat(EstatFactura.ESBORRANY);
        factura.setClient(pressupost.getClient());
        factura.setFormaPagament(pressupost.getFormaPagament());
        factura.setObservacions(pressupost.getObservacions());
        for (LiniaPressupost origen : pressupost.getLinies()) {
            LiniaFactura linia = new LiniaFactura();
            linia.setFactura(factura);
            linia.setProducte(origen.getProducte());
            linia.setDescripcio(origen.getDescripcio());
            linia.setQuantitat(origen.getQuantitat());
            linia.setPreu(origen.getPreu());
            linia.setDte(origen.getDte());
            linia.setIva(origen.getIva());
            linia.setActiu(true);
            factura.getLinies().add(linia);
        }
        factura.setSubtotal(pressupost.getSubtotal());
        factura.setIva(pressupost.getIva());
        factura.setTotal(pressupost.getTotal());
        return factura;
    }

    public void marcarPressupostFacturat(Long pressupostId) {
        Pressupost pressupost = repository.buscarPerIdAmbLinies(pressupostId).orElseThrow(() -> new PressupostNoExisteixException("El pressupost no existeix."));
        pressupost.setEstat(EstatPressupost.FACTURAT);
        repository.actualitzar(pressupost);
    }
}