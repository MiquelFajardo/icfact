package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.factura.model.LiniaFactura;
import cat.informaticassa.icfact.factura.service.CrearFacturaService;
import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

public class ConvertirPressupostAFacturaService {

    private final PressupostRepository repository = new PressupostRepository();
    private final CrearFacturaService crearFacturaService = new CrearFacturaService();

    public Factura executar(Long pressupostId) {
        Pressupost pressupost = repository.buscarPerIdAmbLinies(pressupostId)
                .orElseThrow(() -> new PressupostNoExisteixException("El pressupost no existeix."));

        Factura factura = new Factura();

        factura.setData(pressupost.getData());
        factura.setEstat(cat.informaticassa.icfact.factura.model.EstatFactura.ESBORRANY);
        factura.setClient(pressupost.getClient());
        factura.setObservacions(pressupost.getObservacions());

        for (LiniaPressupost origen : pressupost.getLinies()) {
            LiniaFactura linia = new LiniaFactura();
            linia.setFactura(factura);
            linia.setProducte(origen.getProducte());
            linia.setDescripcio(origen.getDescripcio());
            linia.setQuantitat(origen.getQuantitat());
            linia.setPreu(origen.getPreu());
            linia.setDte(origen.getDte());
            linia.setIva(origen.getIva());
            linia.setActiu(true);
            factura.getLinies().add(linia);
        }

        Factura resultat = crearFacturaService.executar(factura);
        pressupost.setEstat(EstatPressupost.FACTURAT);
        repository.actualitzar(pressupost);

        return resultat;
    }
}