package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;

public class EliminarPagamentService {

    private final PagamentRepository repository = new PagamentRepository();
    private final ModificarEstatFacturaPagamentService actualitzarService = new ModificarEstatFacturaPagamentService();

    public void executar(Pagament pagament) {
        repository.desactivar(pagament);
        actualitzarService.executar(pagament.getFactura());
    }
}