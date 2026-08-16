package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;

import java.util.List;

public class BuscarFacturaService {
    private final FacturaRepository repository = new FacturaRepository();

    public List<Factura> buscar(String text, boolean actives, boolean inactives, Client client) {
        return repository.buscar(text, actives, inactives, client );
    }

    public List<Factura> buscarPendents() {
        return repository.buscarPendents();
    }
}