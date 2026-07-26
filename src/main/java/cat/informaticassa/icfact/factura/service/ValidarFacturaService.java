package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.factura.exception.FacturaSenseLiniesException;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ValidarFacturaService {

    public void executar(Factura factura) {

        validarClient(factura);

        validarLinies(factura);

        validarImports(factura);

        validarDates(factura);
    }

    private void validarClient(Factura factura) {
        Client client = factura.getClient();

        if (client == null || client.getId() == null) {
            throw new IllegalArgumentException("La factura ha de tenir un client.");
        }

        if (!client.isActiu()) {
            throw new IllegalArgumentException("El client està inactiu.");
        }
    }

    private void validarLinies(Factura factura) {
        if (factura.getLinies().isEmpty()) {
            throw new FacturaSenseLiniesException("La factura ha de tenir almenys una línia.");
        }

        for (LiniaFactura linia : factura.getLinies()) {
            if (linia.getQuantitat() == null ||
                    linia.getQuantitat().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("La quantitat ha de ser superior a 0.");
            }

            if (linia.getPreu() == null) {
                throw new IllegalArgumentException("El preu és obligatori.");
            }

            if (linia.getDte() == null) {
                throw new IllegalArgumentException("El descompte és obligatori.");
            }

            if (linia.getIva() == null) {
                throw new IllegalArgumentException("L'IVA és obligatori.");
            }
        }
    }

    private void validarImports(Factura factura) {
        if (factura.getSubtotal() == null) {
            factura.setSubtotal(BigDecimal.ZERO);
        }

        if (factura.getIva() == null) {
            factura.setIva(BigDecimal.ZERO);
        }

        if (factura.getTotal() == null) {
            factura.setTotal(BigDecimal.ZERO);
        }
    }

    private void validarDates(Factura factura) {
        if (factura.getData() == null) {
            factura.setData(LocalDate.now());
        }
    }
}