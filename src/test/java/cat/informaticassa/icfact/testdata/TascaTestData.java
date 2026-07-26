package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;
import java.time.LocalDate;

public final class TascaTestData {
    private static final TascaRepository repository = new TascaRepository();

    private TascaTestData() {
    }

    public static void carregar() {
        guardar("Trucar al client", LocalDate.now().plusDays(2));
        guardar("Enviar pressupost", LocalDate.now().plusDays(5));
        guardar("Comprar tòner", null);
    }

    private static void guardar(String titol, LocalDate dataLimit) {
        Tasca tasca = new Tasca();
        tasca.setTitol(titol);
        tasca.setDataLimit(dataLimit);
        repository.guardar(tasca);
    }
}