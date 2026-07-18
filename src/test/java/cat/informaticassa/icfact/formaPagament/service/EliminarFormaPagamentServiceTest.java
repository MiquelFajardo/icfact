package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EliminarFormaPagamentServiceTest extends BaseRepositoryTest {

    private final EliminarFormaPagamentService service = new EliminarFormaPagamentService();
    private final FormaPagamentRepository repository = new FormaPagamentRepository();

    @Test
    void eliminarFormaPagament() {

        FormaPagament forma = repository.buscarPerNom("Bizum").orElseThrow();

        service.executar(forma);

        assertTrue(repository.buscarPerNom("Bizum").isEmpty());
    }
}