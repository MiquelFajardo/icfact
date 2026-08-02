package cat.informaticassa.icfact.client.repository;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import java.util.List;
import java.util.Optional;

public class ClientRepository extends AbstractActivableRepository<Client, Long> {
    @Override
    protected String ordrePerDefecte() {
        return "nom";
    }

    public Optional<Client> buscarPerNif(String nif) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Client
                    WHERE nif = :nif
                    AND actiu = true
                    """, Client.class)
                    .setParameter("nif", nif)
                    .uniqueResultOptional();
        }
    }

    public List<Client> buscarPerNom(String nom) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Client
                    WHERE lower(nom) LIKE lower(:nom)
                    AND actiu = true
                    ORDER BY nom
                    """, Client.class)
                    .setParameter("nom", "%" + nom + "%")
                    .list();
        }
    }

    @Override
    public List<Client> buscarTots() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
            SELECT c
            FROM Client c
            LEFT JOIN FETCH c.adreca a
            LEFT JOIN FETCH a.poblacio
            LEFT JOIN FETCH a.provincia
            LEFT JOIN FETCH a.pais
            WHERE c.actiu = true
            ORDER BY c.nom
            """, Client.class)
                    .list();
        }
    }

    @Override
    public List<Client> buscarInactius() {

        try (var session = obrirSessio()) {
            return session.createQuery("""
            SELECT c
            FROM Client c
            LEFT JOIN FETCH c.adreca a
            LEFT JOIN FETCH a.poblacio
            LEFT JOIN FETCH a.provincia
            LEFT JOIN FETCH a.pais
            WHERE c.actiu = false
            ORDER BY c.nom
            """, Client.class)
                    .list();
        }
    }

    public List<Client> buscar(String text, boolean actius, boolean inactius) {
        try (var session = obrirSessio()) {
            StringBuilder hql = new StringBuilder("""
            SELECT DISTINCT c
            FROM Client c
            LEFT JOIN FETCH c.adreca a
            LEFT JOIN FETCH a.poblacio
            LEFT JOIN FETCH a.provincia
            LEFT JOIN FETCH a.pais
            WHERE (
                lower(c.nom) LIKE :text
                OR lower(coalesce(c.nomComercial,'')) LIKE :text
                OR lower(c.nif) LIKE :text
                OR lower(coalesce(c.telefon,'')) LIKE :text
                OR lower(coalesce(c.mobil,'')) LIKE :text
                OR lower(coalesce(c.email,'')) LIKE :text
            )
            """);

            if (actius && !inactius) {
                hql.append(" AND c.actiu = true");
            } else if (!actius && inactius) {
                hql.append(" AND c.actiu = false");
            }
            hql.append(" ORDER BY c.nom");
            return session.createQuery(hql.toString(), Client.class)
                    .setParameter("text", "%" + text.toLowerCase() + "%")
                    .list();
        }
    }
}