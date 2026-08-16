package cat.informaticassa.icfact.infraestructura.service;

import cat.informaticassa.icfact.infraestructura.model.TipusDocument;
import cat.informaticassa.icfact.infraestructura.repository.NumeracioDocumentRepository;

public class ObtenirSeguentNumeroDocumentService {
    private final NumeracioDocumentRepository repository = new NumeracioDocumentRepository();

    public long obtenir(int any, TipusDocument tipusDocument) {
        return repository.obtenirSeguentNumero(any, tipusDocument);
    }
}