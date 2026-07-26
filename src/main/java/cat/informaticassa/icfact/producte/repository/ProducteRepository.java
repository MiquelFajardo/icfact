package cat.informaticassa.icfact.producte.repository;

import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.producte.model.Producte;
import java.util.List;
import java.util.Optional;

public class ProducteRepository extends AbstractActivableRepository<Producte, Long> {
    @Override
    protected String ordrePerDefecte() {
        return "nom";
    }

    public Optional<Producte> buscarPerCodi(String codi) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Producte
                    WHERE codi = :codi
                    AND actiu = true
                    """, Producte.class)
                    .setParameter("codi", codi)
                    .uniqueResultOptional();
        }
    }

    public List<Producte> buscarPerNom(String nom) {
        try (var session = obrirSessio()) {

            return session.createQuery("""
                    FROM Producte
                    WHERE lower(nom) LIKE lower(:nom)
                    AND actiu = true
                    ORDER BY nom
                    """, Producte.class)
                    .setParameter("nom", "%" + nom + "%")
                    .list();
        }
    }
}