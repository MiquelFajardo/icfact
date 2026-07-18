package cat.informaticassa.icfact.pdf.pressupost;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.pdf.PdfFonts;
import cat.informaticassa.icfact.pdf.PdfUtils;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;

public class PdfTotalsPressupost {

    public void afegir(Document document,
                       Pressupost pressupost,
                       Empresa empresa) {

        try {

            Color colorEmpresa = Color.decode(empresa.getColor());

            Font normal = PdfFonts.normal();
            Font etiquetaTotals = PdfFonts.negreta();
            Font totalFinal = PdfFonts.total(colorEmpresa);

            Paragraph espai = new Paragraph();
            espai.setSpacingAfter(15f);
            document.add(espai);

            PdfPTable totals = new PdfPTable(2);
            totals.setWidthPercentage(35);
            totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.setWidths(new float[]{55, 45});

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Subtotal", etiquetaTotals));
            cell.setBorder(Rectangle.NO_BORDER);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(PdfUtils.formatImport(pressupost.getSubtotal()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase("IVA", etiquetaTotals));
            cell.setBorder(Rectangle.NO_BORDER);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(PdfUtils.formatImport(pressupost.getIva()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(cell);

            cell.addElement(new Paragraph(" "));
            cell = new PdfPCell(new Phrase("TOTAL", totalFinal));
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(colorEmpresa);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(PdfUtils.formatImport(pressupost.getTotal()), totalFinal));
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(colorEmpresa);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(cell);

            document.add(totals);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}