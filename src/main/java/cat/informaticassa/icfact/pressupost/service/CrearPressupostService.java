package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.infraestructura.model.TipusDocument;
import cat.informaticassa.icfact.infraestructura.service.GenerarNumeroDocumentService;
import cat.informaticassa.icfact.infraestructura.service.ObtenirSeguentNumeroDocumentService;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.LiniaPressupostRepository;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import java.time.LocalDateTime;
import java.time.Year;

public class CrearPressupostService {

    private final PressupostRepository repository = new PressupostRepository();
    private final ObtenirSeguentNumeroDocumentService numeroService = new ObtenirSeguentNumeroDocumentService();
    private final RecalcularPressupostService recalcularService = new RecalcularPressupostService();
    private final ValidarPressupostService validarService = new ValidarPressupostService();

    public Pressupost executar(Pressupost pressupost) {
        validarService.executar(pressupost);
        recalcularService.executar(pressupost);
        long numero = numeroService.obtenir(Year.now().getValue(), TipusDocument.PRESSUPOST);
        pressupost.setNumero(GenerarNumeroDocumentService.generar("P", numero));
        pressupost.setActiu(true);
        pressupost.setDataCreacio(LocalDateTime.now());
        pressupost.setDataModificacio(LocalDateTime.now());
        for (LiniaPressupost linia : pressupost.getLinies()) {
            linia.setPressupost(pressupost);
        }
        repository.guardar(pressupost);
        return pressupost;
    }

    public String generarNumero() {
        long numero = numeroService.obtenir(Year.now().getValue(), TipusDocument.PRESSUPOST);
        return GenerarNumeroDocumentService.generar("P", numero);
    }
}