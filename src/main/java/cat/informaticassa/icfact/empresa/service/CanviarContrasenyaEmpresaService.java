package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.exception.ContrasenyaIncorrectaException;
import cat.informaticassa.icfact.empresa.exception.EmpresaNoExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

public class CanviarContrasenyaEmpresaService {

    private final EmpresaRepository repository = new EmpresaRepository();

    public void executar(String contrasenyaActual, String contrasenyaNova) {
        Empresa empresa = repository.buscar().orElseThrow(() -> new EmpresaNoExisteixException("No existeix cap empresa."));

        if (!BCrypt.checkpw(contrasenyaActual, empresa.getContrasenyaHash())) {
            throw new ContrasenyaIncorrectaException("La contrasenya actual no és correcta.");
        }

        if (contrasenyaNova == null || contrasenyaNova.isBlank()) {
            throw new IllegalArgumentException("La nova contrasenya és obligatòria.");
        }

        empresa.setContrasenyaHash(BCrypt.hashpw(contrasenyaNova, BCrypt.gensalt()));

        empresa.setDataModificacio(LocalDateTime.now());

        repository.actualitzar(empresa);
    }
}