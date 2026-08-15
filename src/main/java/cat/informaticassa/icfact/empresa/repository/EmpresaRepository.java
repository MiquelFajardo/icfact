package cat.informaticassa.icfact.empresa.repository;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.infraestructura.repository.AbstractRepository;
import java.util.Optional;

public class EmpresaRepository extends AbstractRepository<Empresa, Long> {
    public Optional<Empresa> buscar() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT e
                    FROM Empresa e
                    LEFT JOIN FETCH e.adreca a
                    LEFT JOIN FETCH a.poblacio p
                    LEFT JOIN FETCH p.provincia pr
                    LEFT JOIN FETCH pr.pais
                    """, Empresa.class)
                    .setMaxResults(1)
                    .uniqueResultOptional();
        }
    }
}