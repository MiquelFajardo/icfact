package cat.informaticassa.icfact.empresa.service;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import org.mindrot.jbcrypt.BCrypt;

public class RecuperarContrasenyaService {
    private final EmpresaRepository repository =  new EmpresaRepository();

    public boolean executar(Empresa empresa, String clauRecuperacio, String novaContrasenya) {
        if (empresa.getClauRecuperacioHash() == null || empresa.getClauRecuperacioHash().isBlank()) {
            return false;
        }
        if (!BCrypt.checkpw( clauRecuperacio, empresa.getClauRecuperacioHash())) {
            return false;
        }
        String hash = BCrypt.hashpw(novaContrasenya, BCrypt.gensalt());
        empresa.setContrasenyaHash(hash);
        repository.actualitzarContrasenya(empresa.getId(), hash);
        return true;
    }
}