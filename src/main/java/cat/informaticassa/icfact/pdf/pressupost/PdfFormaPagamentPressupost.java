package cat.informaticassa.icfact.pdf.pressupost;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.pdf.PdfFonts;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;

import java.awt.*;

public class PdfFormaPagamentPressupost {

    public void afegir(Document document,
                       Pressupost pressupost,
                       Empresa empresa) {

        try {

            Color colorEmpresa = Color.decode(empresa.getColor());

            Font titol = PdfFonts.titolSeccio(colorEmpresa);
            Font normal = PdfFonts.normalPetit();

            Paragraph espai = new Paragraph();
            espai.setSpacingBefore(20f);
            document.add(espai);

            document.add(new Paragraph("FORMA DE PAGAMENT", titol));

            document.add(new Paragraph(
                    "Forma: " + pressupost.getFormaPagament().getNom(),
                    normal));

            document.add(new Paragraph(
                    pressupost.getFormaPagament().getDescripcio(),
                    normal));

            if (pressupost.getFormaPagament().getMostrarIban()) {

                document.add(new Paragraph(
                        "IBAN: " + empresa.getIban(),
                        normal));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}