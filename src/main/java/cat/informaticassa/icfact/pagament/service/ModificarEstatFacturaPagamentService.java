package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;

import java.math.BigDecimal;

public class ModificarEstatFacturaPagamentService {

    private final PagamentRepository pagamentRepository = new PagamentRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    public void executar(Factura factura) {

        factura = facturaRepository.buscarPerId(factura.getId()).orElseThrow();

        BigDecimal importPagat = pagamentRepository.calcularImportPagat(factura);

        if (importPagat.compareTo(factura.getTotal()) >= 0) {
            factura.setEstat(EstatFactura.COBRADA);
            if (factura.getDataCobrament() == null) {
                factura.setDataCobrament(java.time.LocalDate.now());
            }
        } else {
            factura.setEstat(EstatFactura.EMESA);
            factura.setDataCobrament(null);
        }
        facturaRepository.actualitzar(factura);
    }
}