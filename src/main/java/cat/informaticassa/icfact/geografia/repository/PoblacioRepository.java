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
            return Optional.ofNullable(session.find(Poblacio.class, id));
        }
    }

    @Override
    public List<Poblacio> buscarTots() {

        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Poblacio
                    ORDER BY nom
                    """, Poblacio.class)
                    .list();
        }
    }

    public List<Poblacio> buscarPerProvincia(Provincia provincia) {

        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Poblacio
                    WHERE provincia = :provincia
                    ORDER BY nom
                    """, Poblacio.class)
                    .setParameter("provincia", provincia)
                    .list();
        }
    }

    public Optional<Poblacio> buscarPerNomIProvincia(String nom, Provincia provincia) {
        return buscarPerNom(provincia, nom);
    }

    public Optional<Poblacio> buscarPerNom(Provincia provincia, String nom) {

        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Poblacio
                    WHERE provincia = :provincia
                    AND lower(nom) = lower(:nom)
                    """, Poblacio.class)
                    .setParameter("provincia", provincia)
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }

    public List<Poblacio> buscarPerCodiPostal(String codiPostal) {

        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Poblacio
                    WHERE codiPostal = :codiPostal
                    ORDER BY nom
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
                    FROM Poblacio
                    WHERE provincia = :provincia
                    AND lower(nom) = lower(:nom)
                    AND codiPostal = :codiPostal
                    """, Poblacio.class)
                    .setParameter("provincia", provincia)
                    .setParameter("nom", nom)
                    .setParameter("codiPostal", codiPostal)
                    .uniqueResultOptional();
        }
    }
}