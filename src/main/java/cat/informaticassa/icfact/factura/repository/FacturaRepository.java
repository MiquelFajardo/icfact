package cat.informaticassa.icfact.factura.repository;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FacturaRepository implements Repository<Factura, Long> {

    @Override
    public void guardar(Factura factura) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            System.out.println("ABANS PERSIST");
            System.out.println("Linies = " + factura.getLinies().size());
            System.out.println("Subtotal = " + factura.getSubtotal());
            System.out.println("IVA = " + factura.getIva());
            System.out.println("Total = " + factura.getTotal());
            session.persist(factura);
            tx.commit();
        }
    }

    @Override
    public void actualitzar(Factura factura) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.merge(factura);
            tx.commit();
        }
    }

    @Override
    public Optional<Factura> buscarPerId(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Factura
                    WHERE id = :id
                    AND actiu = true
                    """, Factura.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public Optional<Factura> buscarPerIdIncloentInactius(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    SELECT DISTINCT f
                    FROM Factura f
                    LEFT JOIN FETCH f.linies l
                    LEFT JOIN FETCH l.iva
                    WHERE f.id = :id
                    """, Factura.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public Optional<Factura> buscarPerIdAmbLinies(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                SELECT DISTINCT f
                FROM Factura f
                LEFT JOIN FETCH f.client
                LEFT JOIN FETCH p.formaPagament
                LEFT JOIN FETCH f.linies l
                LEFT JOIN FETCH l.iva
                LEFT JOIN FETCH l.producte
                WHERE f.id = :id
                AND f.actiu = true
                """, Factura.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Factura> buscarTots() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Factura
                    WHERE actiu = true
                    ORDER BY data DESC
                    """, Factura.class)
                    .list();
        }
    }

    public List<Factura> buscarActius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Factura
                    WHERE actiu = true
                    ORDER BY data DESC
                    """, Factura.class)
                    .list();
        }
    }

    public List<Factura> buscarInactius() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Factura
                    WHERE actiu = false
                    ORDER BY data DESC
                    """, Factura.class)
                    .list();
        }
    }

    public Optional<Factura> buscarPerNumero(String numero) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                SELECT DISTINCT f
                FROM Factura f
                LEFT JOIN FETCH f.client
                LEFT JOIN FETCH f.linies l
                LEFT JOIN FETCH l.iva
                LEFT JOIN FETCH l.producte
                WHERE f.numero = :numero
                AND f.actiu = true
                """, Factura.class)
                    .setParameter("numero", numero)
                    .uniqueResultOptional();
        }
    }

    public List<Factura> buscarPerData(LocalDate data) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                    FROM Factura
                    WHERE data = :data
                    AND actiu = true
                    """, Factura.class)
                    .setParameter("data", data)
                    .list();
        }
    }

    @Override
    public void eliminar(Factura factura) {
        factura.setActiu(false);
        actualitzar(factura);
    }

    @Override
    public void activar(Factura factura) {
        factura.setActiu(true);
        actualitzar(factura);
    }

    public long obtenirSeguentNumero(int any) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {

            String prefix = "F" + any;

            Long ultimNumero = session.createQuery("""
                    SELECT MAX(CAST(SUBSTRING(f.numero, 5) AS long))
                    FROM Factura f
                    WHERE f.numero LIKE :prefix
                    """, Long.class)
                    .setParameter("prefix", prefix + "%")
                    .uniqueResult();

            return ultimNumero == null ? 1 : ultimNumero + 1;
        }
    }
}