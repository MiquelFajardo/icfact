package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.tasca.exception.TascaException;
import cat.informaticassa.icfact.tasca.model.Tasca;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidarTascaServiceTest {

    private final ValidarTascaService service = new ValidarTascaService();

    @Test
    void validarCorrecte() {
        Tasca tasca = new Tasca();
        tasca.setTitol("Trucar al client");
        assertDoesNotThrow(() -> service.executar(tasca));
    }

    @Test
    void validarSenseTitol() {
        Tasca tasca = new Tasca();
        assertThrows(TascaException.class,() -> service.executar(tasca));
    }

    @Test
    void validarTitolBuit() {
        Tasca tasca = new Tasca();
        tasca.setTitol("");
        assertThrows(TascaException.class,() -> service.executar(tasca));
    }

    @Test
    void validarTitolMassaLlarg() {
        Tasca tasca = new Tasca();
        tasca.setTitol("A".repeat(151));
        assertThrows(TascaException.class,() -> service.executar(tasca));
    }
}