package cat.informaticassa.icfact.ui.main.components.empresa;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.main.components.color.ColorPane;
import cat.informaticassa.icfact.ui.main.components.pdf.PeuPdfPane;

public class EmpresaBinder {
    private final DadesEmpresaPane dadesEmpresa;
    private final ColorPane color;
    private final PeuPdfPane peuPdf;

    public EmpresaBinder(DadesEmpresaPane dadesEmpresa, ColorPane color, PeuPdfPane peuPdf) {
        this.dadesEmpresa = dadesEmpresa;
        this.color = color;
        this.peuPdf = peuPdf;
    }

    public void carregar(Empresa empresa) {
        dadesEmpresa.mostrar(empresa);
        color.mostrar(empresa);
        peuPdf.mostrar(empresa);
    }

    public void actualitzar(Empresa empresa) {
        dadesEmpresa.actualitzar(empresa);
        color.actualitzar(empresa);
        peuPdf.actualitzar(empresa);
    }

    public void copiar(Empresa origen, Empresa desti) {
        desti.setNom(origen.getNom());
        desti.setDescripcio(origen.getDescripcio());
        desti.setNif(origen.getNif());
        desti.setTelefon(origen.getTelefon());
        desti.setEmail(origen.getEmail());
        desti.setWeb(origen.getWeb());
        desti.setIban(origen.getIban());
        desti.setColor(origen.getColor());
        desti.setPeuPdf(origen.getPeuPdf());
        if (origen.getAdreca() != null) {
            desti.setAdreca(origen.getAdreca());
        }
    }
}