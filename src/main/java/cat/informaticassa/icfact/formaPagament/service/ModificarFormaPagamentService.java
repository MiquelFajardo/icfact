package cat.informaticassa.icfact.formaPagament.service;


import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;

public class ModificarFormaPagamentService {

    private final FormaPagamentRepository repository = new FormaPagamentRepository();
    private final ValidarFormaPagamentService validarService = new ValidarFormaPagamentService();

    public void executar(FormaPagament formaPagament) {
        validarService.executar(formaPagament);
        repository.actualitzar(formaPagament);
    }
}