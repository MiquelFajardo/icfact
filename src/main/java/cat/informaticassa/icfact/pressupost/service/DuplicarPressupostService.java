package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.infraestructura.model.TipusDocument;
import cat.informaticassa.icfact.infraestructura.service.GenerarNumeroDocumentService;
import cat.informaticassa.icfact.infraestructura.service.ObtenirSeguentNumeroDocumentService;
import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.EstatPressupost;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

public class DuplicarPressupostService {

    private final PressupostRepository repository = new PressupostRepository();
    private final CrearPressupostService crearService = new CrearPressupostService();
    private final ObtenirSeguentNumeroDocumentService numeroService = new ObtenirSeguentNumeroDocumentService();

    public Pressupost executar(Long id) {

        Pressupost origen = repository.buscarPerIdAmbLinies(id)
                .orElseThrow(() ->
                        new PressupostNoExisteixException("El pressupost no existeix."));

        Pressupost nou = new Pressupost();

        nou.setClient(origen.getClient());
        nou.setData(LocalDate.now());
        nou.setObservacions(origen.getObservacions());
        nou.setFormaPagament(origen.getFormaPagament());
        nou.setEstat(EstatPressupost.ESBORRANY);
        nou.setActiu(true);
        nou.setDataCreacio(LocalDateTime.now());
        nou.setDataModificacio(LocalDateTime.now());

        long numero = numeroService.obtenir(
                Year.now().getValue(),
                TipusDocument.PRESSUPOST
        );
        nou.setNumero(GenerarNumeroDocumentService.generar("P", numero));

        for (LiniaPressupost liniaOrigen : origen.getLinies()) {
            LiniaPressupost linia = new LiniaPressupost();
            linia.setPressupost(nou);
            linia.setProducte(liniaOrigen.getProducte());
            linia.setDescripcio(liniaOrigen.getDescripcio());
            linia.setQuantitat(liniaOrigen.getQuantitat());
            linia.setPreu(liniaOrigen.getPreu());
            linia.setDte(liniaOrigen.getDte());
            linia.setIva(liniaOrigen.getIva());
            linia.setActiu(true);

            nou.getLinies().add(linia);
        }

        return nou;
    }
}