package cat.informaticassa.icfact.infraestructura.repository;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractRepository<T, ID> {
    protected final Class<T> classe;

    protected AbstractRepository() {
        //noinspection unchecked
        this.classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session obrirSessio() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public void guardar(T entitat) {
        try (Session session = obrirSessio()) {
            var tx = session.beginTransaction();
            session.persist(entitat);
            tx.commit();
        }
    }

    public void actualitzar(T entitat) {
        try (Session session = obrirSessio()) {
            var tx = session.beginTransaction();
            session.merge(entitat);
            tx.commit();
        }
    }
}