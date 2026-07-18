package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.exception.EmpresaJaExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

public class CrearEmpresaService {
    private final EmpresaRepository repository = new EmpresaRepository();

    public void executar(Empresa empresa) {
        if (repository.buscar().isPresent()) {
            throw new EmpresaJaExisteixException("Ja existeix una empresa registrada.");
        }

        empresa.setDataCreacio(LocalDateTime.now());
        empresa.setDataModificacio(LocalDateTime.now());

        if (empresa.getContrasenyaHash() != null && !empresa.getContrasenyaHash().isBlank()) {
            empresa.setContrasenyaHash(BCrypt.hashpw(empresa.getContrasenyaHash(), BCrypt.gensalt()));
        }

        repository.guardar(empresa);
    }
}