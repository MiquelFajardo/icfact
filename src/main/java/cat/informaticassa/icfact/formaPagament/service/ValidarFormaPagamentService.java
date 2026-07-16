package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.formaPagament.exception.FormaPagamentDuplicadaException;
import cat.informaticassa.icfact.formaPagament.exception.FormaPagamentSenseNomException;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;

public class ValidarFormaPagamentService {

    private final FormaPagamentRepository repository = new FormaPagamentRepository();

    public void executar(FormaPagament formaPagament) {

        if (formaPagament.getNom() == null || formaPagament.getNom().isBlank()) {
            throw new FormaPagamentSenseNomException();
        }

        repository.buscarPerNom(formaPagament.getNom())
                .filter(fp -> !fp.getId().equals(formaPagament.getId()))
                .ifPresent(fp -> {
                    throw new FormaPagamentDuplicadaException();
                });
    }
}