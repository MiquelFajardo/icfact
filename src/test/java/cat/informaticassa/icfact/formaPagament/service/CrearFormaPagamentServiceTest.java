package cat.informaticassa.icfact.formaPagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CrearFormaPagamentServiceTest extends BaseRepositoryTest {

    private final CrearFormaPagamentService service = new CrearFormaPagamentService();
    private final FormaPagamentRepository repository = new FormaPagamentRepository();

    @Test
    void crearFormaPagament() {

        FormaPagament forma = FormaPagament.builder()
                .nom("PayPal")
                .descripcio("Pagament per PayPal")
                .mostrarIban(false)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();

        service.executar(forma);

        assertTrue(repository.buscarPerNom("PayPal").isPresent());
    }
}