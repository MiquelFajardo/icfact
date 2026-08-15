package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.exception.FacturaNoExisteixException;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;

import java.time.LocalDateTime;

public class CanviarEstatFacturaService {

    private final FacturaRepository repository = new FacturaRepository();

    public void executar(Long id, EstatFactura estat) {

        Factura factura = repository.buscarPerId(id)
                .orElseThrow(() ->
                        new FacturaNoExisteixException("La factura no existeix."));

        factura.setEstat(estat);
        factura.setDataModificacio(LocalDateTime.now());

        repository.actualitzar(factura);

    }
}