package cat.informaticassa.icfact.infraestructura.repository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractActivableRepository<T, ID>
        extends AbstractRepository<T, ID> {

    protected String ordrePerDefecte() {
        return "id";
    }

    @Override
    public Optional<T> buscarPerId(ID id) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM %s
                    WHERE id = :id
                    AND actiu = true
                    """.formatted(classe.getSimpleName()), classe)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public List<T> buscarTots() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM %s
                    WHERE actiu = true
                    ORDER BY %s
                    """.formatted(classe.getSimpleName(), ordrePerDefecte()), classe)
                    .list();
        }
    }

    public Optional<T> buscarPerIdIncloentInactius(ID id) {
        try (var session = obrirSessio()) {
            return Optional.ofNullable(session.find(classe, id));
        }
    }

    public List<T> buscarInactius() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM %s
                    WHERE actiu = false
                    ORDER BY %s
                    """.formatted(classe.getSimpleName(), ordrePerDefecte()), classe)
                    .list();
        }
    }
}