package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;

public class ModificarPagamentService {

    private final PagamentRepository repository = new PagamentRepository();
    private final ActualitzarEstatFacturaPagamentService actualitzarService =
            new ActualitzarEstatFacturaPagamentService();

    public void executar(Pagament pagament) {

        repository.actualitzar(pagament);

        actualitzarService.executar(pagament.getFactura());
    }
}