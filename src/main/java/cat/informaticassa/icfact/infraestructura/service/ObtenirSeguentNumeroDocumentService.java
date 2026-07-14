package cat.informaticassa.icfact.infraestructura.service;

import cat.informaticassa.icfact.factura.repository.FacturaRepository;
import cat.informaticassa.icfact.infraestructura.model.TipusDocument;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;

public class ObtenirSeguentNumeroDocumentService {

    private final PressupostRepository pressupostRepository = new PressupostRepository();
    private final FacturaRepository facturaRepository = new FacturaRepository();

    public long obtenir(int any, TipusDocument tipusDocument) {

        return switch (tipusDocument) {
            case PRESSUPOST ->
                    pressupostRepository.obtenirSeguentNumero(any);

            case FACTURA ->
                    facturaRepository.obtenirSeguentNumero(any);
        };
    }
}
