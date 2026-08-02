package cat.informaticassa.icfact.geografia.repository;

import cat.informaticassa.icfact.geografia.model.Poblacio;
import cat.informaticassa.icfact.geografia.model.Provincia;
import cat.informaticassa.icfact.infraestructura.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public class PoblacioRepository extends AbstractRepository<Poblacio, Long> {

    @Override
    public Optional<Poblacio> buscarPerId(Long id) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT p
                    FROM Poblacio p
                    JOIN FETCH p.provincia
                    WHERE p.id = :id
                    """, Poblacio.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Poblacio> buscarTots() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT p
                    FROM Poblacio p
                    JOIN FETCH p.provincia
                    ORDER BY p.nom
                    """, Poblacio.class)
                    .list();
        }
    }

    public List<Poblacio> buscarPerProvincia(Provincia provincia) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT p
                    FROM Poblacio p
                    JOIN FETCH p.provincia
                    WHERE p.provincia = :provincia
                    ORDER BY p.nom
                    """, Poblacio.class)
                    .setParameter("provincia", provincia)
                    .list();
        }
    }

    public Optional<Poblacio> buscarPerNomIProvincia(String nom, Provincia provincia) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT p
                    FROM Poblacio p
                    JOIN FETCH p.provincia
                    WHERE p.provincia = :provincia
                    AND lower(p.nom) = lower(:nom)
                    """, Poblacio.class)
                    .setParameter("provincia", provincia)
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }

    public Optional<Poblacio> buscarPerNom(Provincia provincia, String nom) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT p
                    FROM Poblacio p
                    JOIN FETCH p.provincia
                    WHERE p.provincia = :provincia
                    AND lower(p.nom) = lower(:nom)
                    """, Poblacio.class)
                    .setParameter("provincia", provincia)
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }

    public List<Poblacio> buscarPerCodiPostal(String codiPostal) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT p
                    FROM Poblacio p
                    JOIN FETCH p.provincia
                    WHERE :codiPostal MEMBER OF p.codiPostal
                    ORDER BY p.nom
                    """, Poblacio.class)
                    .setParameter("codiPostal", codiPostal)
                    .list();
        }
    }

    public Optional<Poblacio> buscarPerNomICodiPostal(
            Provincia provincia,
            String nom,
            String codiPostal) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    SELECT p
                    FROM Poblacio p
                    JOIN FETCH p.provincia
                    WHERE p.provincia = :provincia
                    AND lower(p.nom) = lower(:nom)
                    AND :codiPostal MEMBER OF p.codiPostal
                    """, Poblacio.class)
                    .setParameter("provincia", provincia)
                    .setParameter("nom", nom)
                    .setParameter("codiPostal", codiPostal)
                    .uniqueResultOptional();
        }
    }
}