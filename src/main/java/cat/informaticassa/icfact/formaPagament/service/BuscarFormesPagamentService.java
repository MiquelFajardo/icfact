package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;

import java.util.List;

public class BuscarFormesPagamentService {

    private final FormaPagamentRepository repository = new FormaPagamentRepository();

    public List<FormaPagament> buscarActius() {
        return repository.buscarActius();
    }

    public List<FormaPagament> buscar(String text, boolean actius, boolean inactius) {
        return repository.buscar(text, actius, inactius);
    }
}