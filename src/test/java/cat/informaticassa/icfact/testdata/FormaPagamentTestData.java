package cat.informaticassa.icfact.testdata;


import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.formaPagament.repository.FormaPagamentRepository;

import java.time.LocalDateTime;

public final class FormaPagamentTestData {

    private static final FormaPagamentRepository repository = new FormaPagamentRepository();

    private FormaPagamentTestData() {
    }

    public static void carregar() {

        guardar(
                "Transferència",
                "100% per transferència bancària.",
                true
        );

        guardar(
                "Transferència 30/70",
                "30% a l'acceptació del pressupost i 70% en finalitzar els treballs.",
                true
        );

        guardar(
                "Targeta",
                "Pagament amb targeta.",
                false
        );

        guardar(
                "Efectiu",
                "Pagament en efectiu.",
                false
        );

        guardar(
                "Bizum",
                "Pagament mitjançant Bizum.",
                false
        );
    }

    private static void guardar(String nom,
                                String descripcio,
                                boolean mostrarIban) {

        FormaPagament forma = FormaPagament.builder()
                .nom(nom)
                .descripcio(descripcio)
                .mostrarIban(mostrarIban)
                .actiu(true)
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();

        repository.guardar(forma);
    }
}