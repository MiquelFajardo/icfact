package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvertirPressupostAFacturaServiceTest extends BaseRepositoryTest {

    private final ConvertirPressupostAFacturaService service = new ConvertirPressupostAFacturaService();
    private final PressupostRepository pressupostRepository = new PressupostRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    @Test
    void convertirPressupostAFactura() {

        Pressupost original = pressupostRepository.buscarPerNumero("P2026000001")
                .orElseThrow();

        Factura factura = service.executar(original.getId());

        assertNotNull(factura.getId());
        assertNotNull(factura.getNumero());

        assertEquals(original.getClient().getId(), factura.getClient().getId());

        assertEquals(original.getLinies().size(), factura.getLinies().size());

        for (int i = 0; i < original.getLinies().size(); i++) {

            LiniaPressupost origen = original.getLinies().get(i);
            LiniaFactura copia = factura.getLinies().get(i);

            assertEquals(origen.getProducte().getId(), copia.getProducte().getId());
            assertEquals(origen.getDescripcio(), copia.getDescripcio());
            assertEquals(0, origen.getQuantitat().compareTo(copia.getQuantitat()));
            assertEquals(0, origen.getPreu().compareTo(copia.getPreu()));
            assertEquals(0, origen.getDte().compareTo(copia.getDte()));
            assertEquals(origen.getIva().getId(), copia.getIva().getId());
        }

        assertEquals(0, original.getSubtotal().compareTo(factura.getSubtotal()));
        assertEquals(0, original.getIva().compareTo(factura.getIva()));
        assertEquals(0, original.getTotal().compareTo(factura.getTotal()));

        Factura bd = facturaRepository.buscarPerIdAmbLinies(factura.getId())
                .orElseThrow();

        assertEquals(factura.getNumero(), bd.getNumero());
        assertEquals(factura.getLinies().size(), bd.getLinies().size());

        Pressupost actualitzat = pressupostRepository.buscarPerIdAmbLinies(original.getId())
                .orElseThrow();

        assertEquals(EstatPressupost.FACTURAT, actualitzat.getEstat());
    }
}