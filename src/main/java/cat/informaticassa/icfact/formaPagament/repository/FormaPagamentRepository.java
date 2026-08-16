package cat.informaticassa.icfact.formaPagament.repository;

import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.infraestructura.repository.AbstractActivableRepository;
import java.util.List;
import java.util.Optional;

public class FormaPagamentRepository extends AbstractActivableRepository<FormaPagament, Long> {

    @Override
    protected String ordrePerDefecte() {
        return "nom";
    }

    public Optional<FormaPagament> buscarPerNom(String nom) {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                    FROM FormaPagament
                    WHERE lower(nom) = lower(:nom)
                    AND actiu = true
                    """, FormaPagament.class)
                    .setParameter("nom", nom)
                    .uniqueResultOptional();
        }
    }

    public List<FormaPagament> buscarActius() {
        try (var session = obrirSessio()) {
            return session.createQuery("""
                FROM FormaPagament
                WHERE actiu = true
                ORDER BY nom
                """, FormaPagament.class).list();
        }
    }

    public List<FormaPagament> buscar(String text, boolean actius, boolean inactius) {
        try (var session = obrirSessio()) {
            StringBuilder hql = new StringBuilder("""
                FROM FormaPagament fp
                WHERE (
                    lower(fp.nom) LIKE :text
                    OR lower(coalesce(fp.descripcio,'')) LIKE :text
                )
                """);

            if (actius && !inactius) {
                hql.append(" AND fp.actiu = true");
            } else if (!actius && inactius) {
                hql.append(" AND fp.actiu = false");
            }

            hql.append(" ORDER BY fp.nom");

            return session.createQuery(hql.toString(), FormaPagament.class)
                    .setParameter("text", "%" + text.toLowerCase() + "%")
                    .list();
        }
    }
}