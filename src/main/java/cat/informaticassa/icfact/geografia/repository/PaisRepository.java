package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.infraestructura.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public class PaisRepository extends AbstractRepository<Pais, Long> {

    @Override
    public Optional<Pais> buscarPerId(Long id) {
        try (var session = obrirSessio()) {
            return Optional.ofNullable(session.find(Pais.class, id));
        }
    }

    public List<Pais> buscarTots() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Pais
                    ORDER BY nom
                    """, Pais.class)
                    .list();
        }
    }

    public Optional<Pais> buscarPerCodiIso(String codiIso) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Pais
                    WHERE codiIso = :codiIso
                    """, Pais.class)
                    .setParameter("codiIso", codiIso)
                    .uniqueResultOptional();
        }
    }

    public Optional<Pais> buscarPerNom(String nom) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Pais
                    WHERE lower(nom) = lower(:nom)
                    """, Pais.class)
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }
}