package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.infraestructura.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public class ProvinciaRepository extends AbstractRepository<Provincia, Long> {

    public List<Provincia> buscarPerPaisId(Long paisId) {
        try (var session = obrirSessio()) {

            return session.createQuery("""
                    FROM Provincia
                    WHERE pais.id = :paisId
                    ORDER BY nom
                    """, Provincia.class)
                    .setParameter("paisId", paisId)
                    .list();
        }
    }

    public List<Provincia> buscarPerPais(Pais pais) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                SELECT p
                FROM Provincia p
                WHERE p.pais.id = :paisId
                ORDER BY p.nom
                """, Provincia.class)
                    .setParameter("paisId", pais.getId())
                    .list();
        }
    }

    public Optional<Provincia> buscarPerNom(Pais pais, String nom) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                FROM Provincia
                WHERE pais.id = :paisId
                AND lower(nom) = lower(:nom)
                """, Provincia.class)
                    .setParameter("paisId", pais.getId())
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }
}