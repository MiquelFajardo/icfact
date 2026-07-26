package cat.informaticassa.icfact.iva.repository;

import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.iva.model.Iva;
import java.math.BigDecimal;
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
}