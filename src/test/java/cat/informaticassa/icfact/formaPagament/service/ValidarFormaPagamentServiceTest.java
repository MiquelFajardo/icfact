package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.formaPagament.exception.FormaPagamentDuplicadaException;
import cat.informaticassa.icfact.formaPagament.exception.FormaPagamentSenseNomException;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidarFormaPagamentServiceTest extends BaseRepositoryTest {

    private final ValidarFormaPagamentService service = new ValidarFormaPagamentService();
    private final FormaPagamentRepository repository = new FormaPagamentRepository();

    @Test
    void validarCorrecte() {

        FormaPagament forma = repository.buscarPerNom("Bizum").orElseThrow();

        assertDoesNotThrow(() -> service.executar(forma));
    }

    @Test
    void validarSenseNom() {

        FormaPagament forma = repository.buscarPerNom("Bizum").orElseThrow();

        forma.setNom("");

        assertThrows(FormaPagamentSenseNomException.class,
                () -> service.executar(forma));
    }

    @Test
    void validarDuplicada() {

        FormaPagament forma = repository.buscarPerNom("Bizum").orElseThrow();

        forma.setNom("Transferència");

        assertThrows(FormaPagamentDuplicadaException.class,
                () -> service.executar(forma));
    }
}