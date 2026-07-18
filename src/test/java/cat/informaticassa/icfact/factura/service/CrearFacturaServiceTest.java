package cat.informaticassa.icfact.factura.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.client.repository.ClientRepository;
import cat.informaticassa.icfact.factura.model.EstatFactura;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;
import cat.informaticassa.icfact.producte.model.Producte;
import cat.informaticassa.icfact.producte.repository.ProducteRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CrearFacturaServiceTest extends BaseRepositoryTest {

    private final CrearFacturaService service = new CrearFacturaService();

    private final FacturaRepository repository = new FacturaRepository();
    private final ClientRepository clientRepository = new ClientRepository();
    private final ProducteRepository producteRepository = new ProducteRepository();
    private final IvaRepository ivaRepository = new IvaRepository();

    @Test
    void crearFactura() {

        Client client = clientRepository.buscarPerNif("12345678A").orElseThrow();
        Producte p1 = producteRepository.buscarPerCodi("P0001").orElseThrow();
        Producte p2 = producteRepository.buscarPerCodi("P0002").orElseThrow();
        Iva iva21 = ivaRepository.buscarPerPercentatge(new BigDecimal("21")).orElseThrow();

        Factura factura = new Factura();

        factura.setClient(client);
        factura.setData(LocalDate.now());
        factura.setEstat(EstatFactura.ESBORRANY);

        LiniaFactura l1 = new LiniaFactura();
        l1.setProducte(p1);
        l1.setDescripcio(p1.getNom());
        l1.setQuantitat(new BigDecimal("2"));
        l1.setPreu(new BigDecimal("35"));
        l1.setDte(BigDecimal.ZERO);
        l1.setIva(iva21);

        LiniaFactura l2 = new LiniaFactura();
        l2.setProducte(p2);
        l2.setDescripcio(p2.getNom());
        l2.setQuantitat(BigDecimal.ONE);
        l2.setPreu(new BigDecimal("60"));
        l2.setDte(new BigDecimal("10"));
        l2.setIva(iva21);

        factura.getLinies().add(l1);
        factura.getLinies().add(l2);

        Factura resultat = service.executar(factura);

        assertNotNull(resultat.getId());
        assertNotNull(resultat.getNumero());

        assertEquals(0, resultat.getSubtotal().compareTo(new BigDecimal("124.00")));
        assertEquals(0, resultat.getIva().compareTo(new BigDecimal("26.04")));
        assertEquals(0, resultat.getTotal().compareTo(new BigDecimal("150.04")));

        Factura bd = repository.buscarPerIdAmbLinies(resultat.getId()).orElseThrow();

        assertEquals(2, bd.getLinies().size());
        assertEquals(0, bd.getTotal().compareTo(new BigDecimal("150.04")));
    }
}