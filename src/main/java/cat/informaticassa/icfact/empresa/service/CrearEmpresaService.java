package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.exception.EmpresaJaExisteixException;
import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class CrearEmpresaService {

    private final EmpresaRepository repository = new EmpresaRepository();

    private static final String CARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String executar(Empresa empresa) {
        if (repository.buscar().isPresent()) {
            throw new EmpresaJaExisteixException("Ja existeix una empresa registrada.");
        }
        empresa.setDataCreacio(LocalDateTime.now());
        empresa.setDataModificacio(LocalDateTime.now());
        String clauRecuperacio = generarClauRecuperacio();
        empresa.setClauRecuperacioHash(BCrypt.hashpw(clauRecuperacio, BCrypt.gensalt()));
        if (empresa.getContrasenyaHash() != null && !empresa.getContrasenyaHash().isBlank()) {
            empresa.setContrasenyaHash(BCrypt.hashpw(empresa.getContrasenyaHash(), BCrypt.gensalt()));
        }
        String color = empresa.getColor();
        if (color == null || color.isBlank()) {
            color = "#2563EB";
        }
        empresa.setColor(color);
        repository.guardar(empresa);
        return clauRecuperacio;
    }

    private String generarClauRecuperacio() {
        StringBuilder clau = new StringBuilder("ICF-");
        for (int grup = 0; grup < 4; grup++) {
            for (int i = 0; i < 4; i++) {
                clau.append(CARACTERS.charAt(RANDOM.nextInt(CARACTERS.length())));
            }
            if (grup < 3) {
                clau.append("-");
            }
        }
        return clau.toString();
    }
}