package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.infraestructura.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public class ProvinciaRepository extends AbstractRepository<Provincia, Long> {

    @Override
    public Optional<Provincia> buscarPerId(Long id) {
        try (var session = obrirSessio()) {
            return Optional.ofNullable(session.find(Provincia.class, id));
        }
    }



    public List<Provincia> buscarPerPais(Pais pais) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Provincia
                    WHERE pais = :pais
                    ORDER BY nom
                    """, Provincia.class)
                    .setParameter("pais", pais)
                    .list();
        }
    }

    public Optional<Provincia> buscarPerNom(Pais pais, String nom) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                FROM Provincia
                WHERE pais = :pais
                AND lower(nom) = lower(:nom)
                """, Provincia.class)
                    .setParameter("pais", pais)
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }
}