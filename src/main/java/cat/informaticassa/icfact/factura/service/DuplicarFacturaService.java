package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.exception.FacturaNoExisteixException;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;

public class DuplicarFacturaService {

    private final FacturaRepository repository = new FacturaRepository();
    private final CrearFacturaService crearService = new CrearFacturaService();

    public Factura executar(Long facturaId) {

        Factura origen = repository.buscarPerIdAmbLinies(facturaId)
                .orElseThrow(() ->
                        new FacturaNoExisteixException("La factura no existeix."));

        Factura copia = new Factura();

        copia.setClient(origen.getClient());
        copia.setData(origen.getData());
        copia.setEstat(EstatFactura.ESBORRANY);
        copia.setObservacions(origen.getObservacions());

        for (LiniaFactura l : origen.getLinies()) {

            LiniaFactura nova = new LiniaFactura();

            nova.setFactura(copia);
            nova.setProducte(l.getProducte());
            nova.setDescripcio(l.getDescripcio());
            nova.setQuantitat(l.getQuantitat());
            nova.setPreu(l.getPreu());
            nova.setDte(l.getDte());
            nova.setIva(l.getIva());

            copia.getLinies().add(nova);
        }

        return crearService.executar(copia);
    }
}