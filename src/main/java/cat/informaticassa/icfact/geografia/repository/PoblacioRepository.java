package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.infraestructura.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public class PoblacioRepository extends AbstractRepository<Poblacio, Long> {
    public List<Poblacio> buscarPerProvincia(Provincia provincia) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                SELECT p
                FROM Poblacio p
                JOIN FETCH p.provincia
                WHERE p.provincia.id = :provinciaId
                ORDER BY p.nom
                """, Poblacio.class)
                    .setParameter("provinciaId", provincia.getId())
                    .list();
        }
    }

    public Optional<Poblacio> buscarPerNom(Provincia provincia, String nom) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                SELECT p
                FROM Poblacio p
                JOIN FETCH p.provincia
                WHERE p.provincia.id = :provinciaId
                AND lower(p.nom) = lower(:nom)
                """, Poblacio.class)
                    .setParameter("provinciaId", provincia.getId())
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }
}