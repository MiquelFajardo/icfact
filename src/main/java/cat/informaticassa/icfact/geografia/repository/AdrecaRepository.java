package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.infraestructura.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public class AdrecaRepository extends AbstractRepository<Adreca, Long> {

    @Override
    public Optional<Adreca> buscarPerId(Long id) {
        try (var session = obrirSessio()) {
            return Optional.ofNullable(session.find(Adreca.class, id));
        }
    }

    @Override
    public List<Adreca> buscarTots() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Adreca
                    ORDER BY carrer, numero
                    """, Adreca.class)
                    .list();
        }
    }

    public List<Adreca> buscarPerPoblacio(Poblacio poblacio) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Adreca
                    WHERE poblacio = :poblacio
                    ORDER BY carrer, numero
                    """, Adreca.class)
                    .setParameter("poblacio", poblacio)
                    .list();
        }
    }
}