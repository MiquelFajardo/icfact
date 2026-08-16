package cat.informaticassa.icfact.informe.service;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InformeService {

    private final FacturaRepository facturaRepository =
            new FacturaRepository();

    private final PagamentRepository pagamentRepository =
            new PagamentRepository();

    public ResumFacturacio facturacio(LocalDate desDe, LocalDate finsA) {
        List<Factura> factures = facturaRepository.buscarPeriodePerInforme(desDe, finsA);
        long nombre = factures.size();
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal cobrat = BigDecimal.ZERO;

        for (Factura factura : factures) {
            subtotal = subtotal.add(valor(factura.getSubtotal()));
            iva = iva.add(valor(factura.getIva()));
            total = total.add(valor(factura.getTotal()));
            cobrat = cobrat.add(pagamentRepository.calcularImportPagat(factura));
        }

        BigDecimal pendent = total.subtract(cobrat);
        if (pendent.compareTo(BigDecimal.ZERO) < 0) {
            pendent = BigDecimal.ZERO;
        }

        return new ResumFacturacio(nombre, subtotal, iva, total, cobrat, pendent);
    }

    public List<ResumIva> iva(LocalDate desDe, LocalDate finsA) {
        List<Factura> factures = facturaRepository.buscarPeriodePerInforme(desDe, finsA);
        Map<BigDecimal, ResumIva> resultat = new LinkedHashMap<>();
        for (Factura factura : factures) {
            for (LiniaFactura linia : factura.getLinies()) {
                if (!linia.isActiu()) {
                    continue;
                }
                BigDecimal percentatge = valor(linia.getIva().getPercentatge());
                ResumIva actual = resultat.get(percentatge);
                BigDecimal base = valor(linia.getSubtotal());
                BigDecimal importIva = valor(linia.getTotal()).subtract(base);
                BigDecimal total = valor(linia.getTotal());
                if (actual == null) {
                    resultat.put(percentatge, new ResumIva(percentatge, base, importIva, total));
                } else {
                    resultat.put(percentatge, new ResumIva(percentatge, actual.base().add(base), actual.importIva().add(importIva), actual.total().add(total)));
                }
            }
        }
        return new ArrayList<>(resultat.values());
    }

    public List<ResumClient> facturacioPerClient(LocalDate desDe, LocalDate finsA) {
        List<Factura> factures = facturaRepository.buscarPeriodePerInforme(desDe, finsA);
        Map<Long, ResumClient> resultat = new LinkedHashMap<>();
        for (Factura factura : factures) {
            if (factura.getClient() == null) {
                continue;
            }
            Long clientId = factura.getClient().getId();
            BigDecimal facturat = valor(factura.getTotal());
            BigDecimal cobrat = pagamentRepository.calcularImportPagat(factura);
            ResumClient actual = resultat.get(clientId);
            if (actual == null) {
                resultat.put(clientId, new ResumClient(factura.getClient().getNom(), facturat, cobrat, pendent(facturat, cobrat)));
            } else {
                BigDecimal nouFacturat = actual.facturat().add(facturat);
                BigDecimal nouCobrat = actual.cobrat().add(cobrat);
                resultat.put(clientId, new ResumClient(actual.client(), nouFacturat, nouCobrat, pendent(nouFacturat, nouCobrat)));
            }
        }
        return new ArrayList<>(resultat.values());
    }

    private BigDecimal pendent(BigDecimal total, BigDecimal cobrat) {
        BigDecimal pendent = total.subtract(cobrat);
        return pendent.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : pendent;
    }

    private BigDecimal valor(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }

    public record ResumFacturacio( long nombreFactures, BigDecimal subtotal, BigDecimal iva, BigDecimal total, BigDecimal cobrat, BigDecimal pendent) {
    }

    public record ResumIva(BigDecimal percentatge, BigDecimal base, BigDecimal importIva, BigDecimal total) {
    }

    public record ResumClient(String client, BigDecimal facturat, BigDecimal cobrat, BigDecimal pendent) {
    }
}