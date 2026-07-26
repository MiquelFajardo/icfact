package cat.informaticassa.icfact.pressupost.repository;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import java.util.List;


public class LiniaPressupostRepository extends AbstractActivableRepository<LiniaPressupost, Long> {

    public List<LiniaPressupost> buscarPerPressupost(Long pressupostId) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM LiniaPressupost
                    WHERE pressupost.id = :id
                    AND actiu = true
                    ORDER BY id
                    """, LiniaPressupost.class)
                    .setParameter("id", pressupostId)
                    .list();
        }
    }
}