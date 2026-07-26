package cat.informaticassa.icfact.infraestructura.repository;

import cat.informaticassa.icfact.infraestructura.model.Activable;

import java.util.List;
import java.util.Optional;

public abstract class AbstractActivableRepository<T extends Activable, ID>
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

    @Override
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

    public void activar(T entitat) {
        entitat.setActiu(true);
        actualitzar(entitat);
    }

    public void desactivar(T entitat) {
        entitat.setActiu(false);
        actualitzar(entitat);
    }
}