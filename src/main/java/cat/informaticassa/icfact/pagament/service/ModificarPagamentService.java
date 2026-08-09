package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;

public class ModificarPagamentService {
    private final PagamentRepository repository =  new PagamentRepository();
    private final ActualitzarEstatPagamentService actualitzarEstatService = new ActualitzarEstatPagamentService();

    public void executar(Pagament pagament) {
        if (pagament.getPressupost() == null && pagament.getFactura() == null) {
            throw new IllegalArgumentException("El pagament ha d'estar relacionat amb un pressupost o una factura.");
        }
        repository.actualitzar(pagament);
        actualitzarEstatService.executar(pagament);
    }
}