package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;

import java.time.LocalDateTime;

public class CrearFormaPagamentService {

    private final FormaPagamentRepository repository = new FormaPagamentRepository();
    private final ValidarFormaPagamentService validarService = new ValidarFormaPagamentService();

    public void executar(FormaPagament formaPagament) {
        validarService.executar(formaPagament);
        LocalDateTime ara = LocalDateTime.now();
        formaPagament.setDataCreacio(ara);
        formaPagament.setDataModificacio(ara);
        repository.guardar(formaPagament);
    }
}