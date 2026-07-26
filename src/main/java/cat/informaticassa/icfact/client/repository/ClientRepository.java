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
}