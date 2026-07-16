package cat.informaticassa.icfact.pdf;

import com.lowagie.text.Font;

import java.awt.Color;

public final class PdfFonts {

    private PdfFonts() {
    }

    public static Font titol(Color color) {
        return new Font(Font.HELVETICA, 18, Font.BOLD, color);
    }

    public static Font titolGran(Color color) {
        return new Font(Font.HELVETICA, 26, Font.BOLD, color);
    }

    public static Font nomEmpresa(Color color) {
        return new Font(Font.HELVETICA, 22, Font.BOLD, color);
    }

    public static Font etiqueta(Color color) {
        return new Font(Font.HELVETICA, 11, Font.BOLD, color);
    }

    public static Font titolSeccio(Color color) {
        return new Font(Font.HELVETICA, 11, Font.BOLD, color);
    }

    public static Font normal() {
        return new Font(Font.HELVETICA, 11);
    }

    public static Font normalPetit() {
        return new Font(Font.HELVETICA, 10);
    }

    public static Font negreta() {
        return new Font(Font.HELVETICA, 10, Font.BOLD);
    }

    public static Font total(Color color) {
        return new Font(Font.HELVETICA, 13, Font.BOLD, color);
    }

    public static Font peuPagina() {
        return new Font(Font.HELVETICA, 9);
    }
}