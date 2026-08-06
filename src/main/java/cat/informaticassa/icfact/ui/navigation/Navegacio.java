package cat.informaticassa.icfact.ui.navigation;

public final class Navegacio {
    private static Navegador navegador;

    private Navegacio() {
    }

    public static void inicialitzar(Navegador nav) {
        navegador = nav;
    }

    public static Navegador get() {
        return navegador;
    }
}