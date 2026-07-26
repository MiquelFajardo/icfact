package cat.informaticassa.icfact.testdata;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import cat.informaticassa.icfact.empresa.service.CrearEmpresaService;
import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.repository.AdrecaRepository;

import java.time.LocalDateTime;

public class EmpresaTestData {
    private static final CrearEmpresaService crearEmpresaService = new CrearEmpresaService();
    private static final AdrecaRepository adrecaRepository = new AdrecaRepository();


    private EmpresaTestData() {
    }

    public static void carregar() {

        Adreca adreca = adrecaRepository.buscarTots().getFirst();

        Empresa empresa = Empresa.builder()
                .nom("Manel Serra")
                .descripcio("Pintor")
                .nif("12345678A")
                .adreca(adreca)
                .telefon("600123123")
                .email("info@ipintorserra.cat")
                .web("https://www.pintorserra.cat")
                .iban("ES2100000000000000000000")
                .logo("logo.png")
                .color("#2563EB")
                .peuPdf("Gràcies per confiar en nosaltres.")
                .contrasenyaHash("contrasenya")
                .dataCreacio(LocalDateTime.now())
                .dataModificacio(LocalDateTime.now())
                .build();

        crearEmpresaService.executar(empresa);
    }
}
