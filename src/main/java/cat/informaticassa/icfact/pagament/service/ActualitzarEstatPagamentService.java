package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ActualitzarEstatPagamentService {
    private final PagamentRepository pagamentRepository = new PagamentRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    public void executar(Pagament pagament) {
        if (pagament.getFactura() == null) {
            return;
        }

        Factura factura = facturaRepository.buscarPerId(pagament.getFactura().getId()).orElseThrow();
        if (factura.getEstat() == EstatFactura.ANULADA) {
            return;
        }

        BigDecimal importPagat = pagamentRepository.calcularImportPagat(factura);
        if (importPagat.compareTo(factura.getTotal()) >= 0) {
            factura.setEstat(EstatFactura.COBRADA);
            if (factura.getDataCobrament() == null) {
                factura.setDataCobrament(LocalDate.now());
            }
        } else {
            factura.setEstat(EstatFactura.EMESA);
            factura.setDataCobrament(null);
        }
        facturaRepository.actualitzar(factura);
    }
}