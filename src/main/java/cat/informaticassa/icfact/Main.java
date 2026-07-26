package cat.informaticassa.icfact;

import cat.informaticassa.icfact.infraestructura.database.HibernateUtil;

public class Main {

    public static void main(String[] args) {
        HibernateUtil.configurarProduccio();
        App.main(args);
    }
}