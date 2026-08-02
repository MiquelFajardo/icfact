package cat.informaticassa.icfact.iva.repository;

import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.iva.model.Iva;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class IvaRepository extends AbstractActivableRepository<Iva, Long> {
    @Override
    protected String ordrePerDefecte() {
        return "percentatge";
    }

    public Optional<Iva> buscarPerPercentatge(BigDecimal percentatge) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM Iva
                    WHERE percentatge = :percentatge
                    AND actiu = true
                    """, Iva.class)
                    .setParameter("percentatge", percentatge)
                    .uniqueResultOptional();
        }
    }

    public List<Iva> buscar(String text, boolean actius, boolean inactius) {
        try (var session = obrirSessio()) {
            StringBuilder hql = new StringBuilder("""
                FROM Iva i
                WHERE (
                    lower(i.nom) LIKE :text
                    OR str(i.percentatge) LIKE :text
                )
                """);
            if (actius && !inactius) {
                hql.append(" AND i.actiu = true");
            } else if (!actius && inactius) {
                hql.append(" AND i.actiu = false");
            }
            hql.append(" ORDER BY i.percentatge");
            return session.createQuery(hql.toString(), Iva.class)
                    .setParameter("text", "%" + text.toLowerCase() + "%")
                    .list();
        }
    }
}