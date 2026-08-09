package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;

public class EliminarPagamentService {
    private final PagamentRepository repository = new PagamentRepository();
    private final ActualitzarEstatPagamentService actualitzarEstatService = new ActualitzarEstatPagamentService();

    public void executar(Pagament pagament) {
        repository.desactivar(pagament);
        actualitzarEstatService.executar(pagament);
    }
}