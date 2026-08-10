package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ComprovarFacturaPagadaService {
    private final PagamentRepository pagamentRepository = new PagamentRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    public void executar(Long facturaId) {
        Factura factura = facturaRepository.buscarPerIdIncloentInactius(facturaId).orElseThrow(() ->
                        new IllegalArgumentException("La factura no existeix."));

        if (factura.getEstat() == EstatFactura.ANULADA) {
            return;
        }
        BigDecimal importPagat = pagamentRepository.calcularImportPagat(factura);
        if (importPagat.compareTo(factura.getTotal()) >= 0) {
            factura.setEstat(EstatFactura.COBRADA);
            factura.setDataCobrament(LocalDate.now());
            facturaRepository.actualitzar(factura);
        }
    }
}