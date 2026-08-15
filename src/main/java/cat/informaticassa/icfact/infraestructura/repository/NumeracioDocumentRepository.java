package cat.informaticassa.icfact.infraestructura.repository;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;
import cat.informaticassa.icfact.infraestructura.model.TipusDocument;

public class NumeracioDocumentRepository {

    public long obtenirSeguentNumero(int any, TipusDocument tipusDocument) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            String entitat = switch (tipusDocument) {
                case FACTURA -> "Factura";
                case PRESSUPOST -> "Pressupost";
            };

            String prefix = switch (tipusDocument) {
                case FACTURA -> "F" + any;
                case PRESSUPOST -> "P" + any;
            };

            String hql = """
                    SELECT MAX(CAST(SUBSTRING(d.numero, 6) AS long))
                    FROM %s d
                    WHERE d.numero LIKE :prefix
                    """.formatted(entitat);

            Long ultimNumero = session.createQuery(hql, Long.class)
                    .setParameter("prefix", prefix + "%")
                    .uniqueResult();

            return ultimNumero == null ? 1 : ultimNumero + 1;
        }
    }
}