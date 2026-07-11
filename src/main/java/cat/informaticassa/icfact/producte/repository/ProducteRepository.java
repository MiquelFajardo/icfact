package cat.informaticassa.icfact.producte.repository;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;
import cat.informaticassa.icfact.producte.model.Producte;

import java.util.List;
import java.util.Optional;

public class ProducteRepository implements Repository<Producte, Long> {

    @Override
    public void guardar(Producte producte) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(producte);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Producte producte) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(producte);
            tx.commit();
        }
    }

    @Override
    public Optional<Producte> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Producte.class, id));
        }
    }

    @Override
    public List<Producte> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producte ORDER BY nom",
                    Producte.class
            ).list();
        }
    }

    @Override
    public void eliminar(Producte producte) {
        producte.setActiu(false);
        actualitzar(producte);
    }

    @Override
    public void activar(Producte producte) {
        producte.setActiu(true);
        actualitzar(producte);
    }

    public Optional<Producte> buscarPerCodi(String codi) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producte WHERE codi = :codi",
                            Producte.class)
                    .setParameter("codi", codi)
                    .uniqueResultOptional();
        }
    }

    public List<Producte> buscarPerNom(String nom) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Producte WHERE lower(nom) like lower(:nom) ORDER BY nom",
                            Producte.class)
                    .setParameter("nom", "%" + nom + "%")
                    .list();
        }
    }

    public List<Producte> buscarActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Producte WHERE actiu = true ORDER BY nom",
                            Producte.class)
                    .list();
        }
    }

    public List<Producte> buscarInactius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Producte WHERE actiu = false ORDER BY nom",
                    Producte.class
            ).list();
        }
    }

}