package cat.informaticassa.icfact.pagament.service;

import cat.informaticassa.icfact.BaseRepositoryTest;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.repository.PagamentRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModificarPagamentServiceTest extends BaseRepositoryTest {

    private final ModificarPagamentService service = new ModificarPagamentService();
    private final PagamentRepository repository = new PagamentRepository();

    @Test
    void modificarPagament() {

        Pagament pagament = repository.buscarTots().getFirst();

        pagament.setImportPagat(new BigDecimal("300.00"));

        service.executar(pagament);

        pagament = repository.buscarPerId(pagament.getId()).orElseThrow();

        assertEquals(0, pagament.getImportPagat().compareTo(new BigDecimal("300.00")));
    }
}