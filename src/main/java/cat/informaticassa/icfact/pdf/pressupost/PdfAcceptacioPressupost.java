package cat.informaticassa.icfact.pdf.pressupost;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.pdf.PdfFonts;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;

public class PdfAcceptacioPressupost {

    public void afegir(Document document, Empresa empresa) {
        try {
            Color colorEmpresa = Color.decode(empresa.getColor());

            Font titol = PdfFonts.titolSeccio(colorEmpresa);
            Font normal = PdfFonts.normalPetit();
            Font negreta = PdfFonts.etiqueta(colorEmpresa);

            Paragraph espai = new Paragraph();
            espai.setSpacingBefore(50f);
            document.add(espai);

            document.add(new Paragraph("ACCEPTACIÓ DEL PRESSUPOST", titol));
            document.add(new Paragraph("Amb la signatura d'aquest document el client accepta aquest pressupost i les seves condicions.", normal));
            document.add(Chunk.NEWLINE);

            PdfPTable signatures = new PdfPTable(2);
            signatures.setWidthPercentage(100);
            signatures.setSpacingBefore(10f);
            signatures.setWidths(new float[]{50,50});

            signatures.addCell(crearBlocClient(normal, negreta, colorEmpresa));
            signatures.addCell(crearBlocEmpresa(normal, negreta, colorEmpresa, empresa));

            document.add(signatures);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private PdfPCell crearBlocClient(Font normal, Font negreta,  Color color) {
        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(color);
        cell.setBorderWidth(1f);
        cell.setPadding(12);

        cell.addElement(new Paragraph("CLIENT", negreta));
        cell.addElement(new Paragraph(" "));
        cell.addElement(new Paragraph(" "));
        cell.addElement(new Paragraph(" "));
        cell.addElement(new Paragraph(" "));
        cell.addElement(new Paragraph("Signatura client        Data: ____ / ____ / ______ ", normal));

        return cell;
    }

    private PdfPCell crearBlocEmpresa(Font normal, Font negreta, Color color, Empresa empresa) {
        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(color);
        cell.setBorderWidth(1f);
        cell.setPadding(12);

        cell.addElement(new Paragraph(empresa.getNom().toUpperCase(), negreta));
        cell.addElement(new Paragraph(" "));
        cell.addElement(new Paragraph(" "));
        cell.addElement(new Paragraph(" "));
        cell.addElement(new Paragraph(" "));
        cell.addElement(new Paragraph(empresa.getNom(), normal));

        return cell;
    }
}