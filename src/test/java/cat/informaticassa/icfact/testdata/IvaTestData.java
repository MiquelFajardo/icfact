package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.iva.repository.IvaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class IvaTestData {

    private static final IvaRepository repository = new IvaRepository();

    private IvaTestData() {
    }

    public static void carregar() {
        guardar("Superreduït", new BigDecimal("4.00"));
        guardar("Reduït", new BigDecimal("10.00"));
        guardar("General", new BigDecimal("21.00"));
        guardar("Exempt", new BigDecimal("0.00"));
    }

    private static void guardar(String nom, BigDecimal percentatge) {
        Iva iva = Iva.builder()
                .nom(nom)
                .percentatge(percentatge)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();
        repository.guardar(iva);
    }
}