package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModificarFormaPagamentServiceTest extends BaseRepositoryTest {

    private final ModificarFormaPagamentService service = new ModificarFormaPagamentService();
    private final FormaPagamentRepository repository = new FormaPagamentRepository();

    @Test
    void modificarFormaPagament() {

        FormaPagament forma = repository.buscarPerNom("Bizum").orElseThrow();

        forma.setDescripcio("Nou text");

        service.executar(forma);

        FormaPagament modificada = repository.buscarPerNom("Bizum").orElseThrow();

        assertEquals("Nou text", modificada.getDescripcio());
    }
}