package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.factura.exception.FacturaNoExisteixException;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;

import java.util.List;

public class BuscarFacturaService {
    private final FacturaRepository repository = new FacturaRepository();

    public Factura executar(Long id) {
        return repository.buscarPerId(id).orElseThrow(() -> new FacturaNoExisteixException("La factura no existeix."));
    }

    public List<Factura> buscarTotes() {
        return repository.buscarTotes();
    }

    public List<Factura> buscarActives() {
        return repository.buscarActives();
    }

    public List<Factura> buscarInactives() {
        return repository.buscarInactives();
    }

    public List<Factura> buscar(String text, boolean actives, boolean inactives, Client client) {
        return repository.buscar(text, actives, inactives, client );
    }

    public List<Factura> buscarPendents() {
        return repository.buscarPendents();
    }
}