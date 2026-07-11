package cat.informaticassa.icfact.client.repository;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;

import java.util.List;
import java.util.Optional;

public class ClientRepository implements Repository<Client, Long> {
    @Override
    public void guardar(Client client) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(client);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Client client) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(client);
            tx.commit();
        }
    }

    @Override
    public Optional<Client> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Client.class, id));
        }
    }

    public Optional<Client> buscarPerNif(String nif) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Client WHERE nif = :nif", Client.class)
                    .setParameter("nif", nif)
                    .uniqueResultOptional();
        }
    }

    public List<Client> buscarPerNom(String nom) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Client WHERE lower(nom) like lower(:nom) ORDER BY nom", Client.class)
                    .setParameter("nom", "%" + nom + "%")
                    .list();
        }
    }

    @Override
    public List<Client> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Client ORDER BY nom", Client.class)
                    .list();
        }
    }

    public List<Client> buscarActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Client WHERE actiu = true ORDER BY nom", Client.class)
                    .list();
        }
    }

    public List<Client> buscarInactius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Client WHERE actiu = false ORDER BY nom",
                    Client.class
            ).list();
        }
    }

    @Override
    public void eliminar(Client client) {
        client.setActiu(false);
        actualitzar(client);
    }

    @Override
    public void activar(Client client) {
        client.setActiu(true);
        actualitzar(client);
    }
}
