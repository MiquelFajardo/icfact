package cat.informaticassa.icfact.empresa.repository;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;

import java.util.Optional;

public class EmpresaRepository {
    public void guardar(Empresa empresa) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(empresa);
            tx.commit();
        }
    }

    public void actualitzar(Empresa empresa) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(empresa);
            tx.commit();
        }
    }

    public Optional<Empresa> buscar() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                SELECT e
                FROM Empresa e
                LEFT JOIN FETCH e.adreca a
                LEFT JOIN FETCH a.poblacio p
                LEFT JOIN FETCH p.provincia pr
                LEFT JOIN FETCH pr.pais
                """, Empresa.class)
                    .setMaxResults(1)
                    .uniqueResultOptional();
        }
    }
}
