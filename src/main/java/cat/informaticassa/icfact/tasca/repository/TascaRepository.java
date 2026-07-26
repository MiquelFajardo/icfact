package cat.informaticassa.icfact.tasca.repository;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.tasca.model.Tasca;
import java.util.List;


public class TascaRepository extends AbstractActivableRepository<Tasca, Long> {

    @Override public List<Tasca> buscarTots() { try (var session = HibernateUtil.getSessionFactory().openSession()) {
        return session.createQuery(""" 
            FROM Tasca
            WHERE actiu = true
            ORDER BY feta ASC,
                dataLimit ASC NULLS LAST,
                dataCreacio DESC
            """, Tasca.class)
                .list();
     }
    }

    public List<Tasca> buscarPendents() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Tasca
                    WHERE actiu = true
                    AND feta = false
                    ORDER BY dataLimit ASC NULLS LAST,
                             dataCreacio DESC
                    """, Tasca.class)
                    .list();
        }
    }

    public List<Tasca> buscarFetes() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Tasca
                    WHERE actiu = true
                    AND feta = true
                    ORDER BY dataModificacio DESC
                    """, Tasca.class)
                    .list();
        }
    }

    @Override
    public List<Tasca> buscarInactius() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                FROM Tasca
                WHERE actiu = false
                ORDER BY dataCreacio DESC
                """, Tasca.class)
                    .list();
        }
    }
}