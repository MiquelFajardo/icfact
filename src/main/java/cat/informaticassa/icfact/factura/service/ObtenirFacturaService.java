package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.factura.exception.FacturaNoExisteixException;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;

public class ObtenirFacturaService {
    private final FacturaRepository repository = new FacturaRepository();

    public Factura executar(Long id) {
        return repository.buscarPerIdAmbLinies(id).orElseThrow(() -> new FacturaNoExisteixException("La factura no existeix."));
    }
}