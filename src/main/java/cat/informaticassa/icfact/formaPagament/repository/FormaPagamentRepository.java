package cat.informaticassa.icfact.formaPagament.repository;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import java.util.List;
import java.util.Optional;

public class FormaPagamentRepository extends AbstractActivableRepository<FormaPagament, Long> {

    @Override
    protected String ordrePerDefecte() {
        return "nom";
    }

    public Optional<FormaPagament> buscarPerNom(String nom) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM FormaPagament
                    WHERE lower(nom) = lower(:nom)
                    AND actiu = true
                    """, FormaPagament.class)
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }
}