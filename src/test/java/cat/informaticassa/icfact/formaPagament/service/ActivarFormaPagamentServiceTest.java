package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivarFormaPagamentServiceTest extends BaseRepositoryTest {

    private final ActivarFormaPagamentService service = new ActivarFormaPagamentService();
    private final FormaPagamentRepository repository = new FormaPagamentRepository();

    @Test
    void activarFormaPagament() {

        FormaPagament forma = repository.buscarPerNom("Bizum").orElseThrow();

        repository.eliminar(forma);

        forma = repository.buscarPerIdIncloentInactius(forma.getId()).orElseThrow();

        service.executar(forma);

        assertTrue(repository.buscarPerNom("Bizum").isPresent());
    }
}