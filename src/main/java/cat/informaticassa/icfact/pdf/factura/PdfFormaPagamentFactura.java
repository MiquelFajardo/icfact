package cat.informaticassa.icfact.pdf.factura;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.pdf.PdfFonts;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;

import java.awt.*;
import java.math.BigDecimal;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PdfFormaPagamentFactura {
    public void afegir(Document document, Factura factura, Empresa empresa) {

        try {

            Color colorEmpresa = Color.decode(empresa.getColor());

            Font titol = PdfFonts.titolSeccio(colorEmpresa);
            Font normal = PdfFonts.normalPetit();
            Font negreta = PdfFonts.negreta();

            Paragraph espai = new Paragraph();
            espai.setSpacingBefore(20f);
            document.add(espai);

            PdfPTable taula = new PdfPTable(1);
            taula.setWidthPercentage(100);

            // ===========================
            // FORMA DE PAGAMENT
            // ===========================

            PdfPCell esquerra = new PdfPCell();
            esquerra.setBorder(Rectangle.NO_BORDER);

            esquerra.addElement(new Paragraph("FORMA DE PAGAMENT", titol));
            esquerra.addElement(new Paragraph(" "));

            esquerra.addElement(new Paragraph(factura.getFormaPagament().getNom(), negreta));

            if (factura.getFormaPagament().getMostrarIban()) {
                esquerra.addElement(new Paragraph(" "));
                esquerra.addElement(new Paragraph("IBAN:", negreta));
                esquerra.addElement(new Paragraph(empresa.getIban(), normal));
            }
            taula.addCell(esquerra);
            document.add(taula);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}