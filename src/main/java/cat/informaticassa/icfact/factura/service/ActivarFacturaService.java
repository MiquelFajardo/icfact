package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.exception.FacturaNoExisteixException;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;

public class ActivarFacturaService {

    private final FacturaRepository repository = new FacturaRepository();

    public void executar(Long id) {

        Factura factura = repository.buscarPerIdIncloentInactius(id)
                .orElseThrow(() ->
                        new FacturaNoExisteixException("La factura no existeix."));

        repository.activar(factura);
    }
}