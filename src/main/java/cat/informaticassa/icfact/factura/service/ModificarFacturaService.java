package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.exception.FacturaNoExisteixException;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;

import java.time.LocalDateTime;

public class ModificarFacturaService {
    private final FacturaRepository repository = new FacturaRepository();
    private final ValidarFacturaService validarService =  new ValidarFacturaService();
    private final RecalcularFacturaService recalcularService =  new RecalcularFacturaService();

    public void executar(Factura factura) {
        Factura existent = repository.buscarPerIdAmbLinies(factura.getId()).orElseThrow(() ->  new FacturaNoExisteixException("La factura no existeix."));
        validarService.executar(factura);
        recalcularService.executar(factura);
        existent.setClient(factura.getClient());
        existent.setData(factura.getData());
        existent.setEstat(factura.getEstat());
        existent.setObservacions(factura.getObservacions());
        existent.setFormaPagament(factura.getFormaPagament());
        existent.getLinies().clear();
        for (LiniaFactura linia : factura.getLinies()) {
            linia.setFactura(existent);
            existent.getLinies().add(linia);
        }
        existent.setSubtotal(factura.getSubtotal());
        existent.setIva(factura.getIva());
        existent.setTotal(factura.getTotal());
        existent.setDataModificacio(LocalDateTime.now());
        repository.actualitzar(existent);
    }
}