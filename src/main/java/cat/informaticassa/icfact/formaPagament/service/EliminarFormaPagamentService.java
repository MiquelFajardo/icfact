package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;

public class EliminarFormaPagamentService {

    private final FormaPagamentRepository repository = new FormaPagamentRepository();

    public void executar(FormaPagament formaPagament) {
        repository.desactivar(formaPagament);
    }
}