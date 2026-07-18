package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;

public class RegistrarPagamentService {

    private final PagamentRepository repository = new PagamentRepository();
    private final ActualitzarEstatFacturaPagamentService actualitzarService =
            new ActualitzarEstatFacturaPagamentService();

    public void executar(Pagament pagament) {

        repository.guardar(pagament);

        actualitzarService.executar(pagament.getFactura());
    }
}