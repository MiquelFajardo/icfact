package cat.informaticassa.icfact.pdf.factura;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.pdf.PdfFonts;
import cat.informaticassa.icfact.pdf.PdfUtils;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;
import java.math.BigDecimal;

public class PdfTotalsFactura {

    public void afegir(Document document, Factura factura, BigDecimal importPagat, Empresa empresa) {

        try {

            Color colorEmpresa = Color.decode(empresa.getColor());

            Font normal = PdfFonts.normal();
            Font etiquetaTotals = PdfFonts.negreta();
            Font totalFinal = PdfFonts.total(colorEmpresa);
            Font fontPagat = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font fontPendent;
            BigDecimal pendent = factura.getTotal().subtract(importPagat);


            if (pendent.compareTo(BigDecimal.ZERO) == 0) {
                fontPendent = new Font(Font.HELVETICA, 13, Font.BOLD, new Color(0, 140, 0));
            } else if (pendent.compareTo(BigDecimal.ZERO) > 0) {
                fontPendent = new Font(Font.HELVETICA, 13, Font.BOLD, new Color(220, 120, 0));
            } else {
                fontPendent = new Font(Font.HELVETICA, 13, Font.BOLD, Color.RED);
            }

            Paragraph espai = new Paragraph();
            espai.setSpacingAfter(25f);
            document.add(espai);

            PdfPTable totals = new PdfPTable(2);
            totals.setWidthPercentage(35);
            totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.setWidths(new float[]{55, 45});

            PdfPCell cell;

            // Subtotal

            cell = new PdfPCell(new Phrase("Subtotal", etiquetaTotals));
            cell.setBorder(Rectangle.NO_BORDER);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(PdfUtils.formatImport(factura.getSubtotal()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(cell);

            // IVA

            cell = new PdfPCell(new Phrase("IVA", etiquetaTotals));
            cell.setBorder(Rectangle.NO_BORDER);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(PdfUtils.formatImport(factura.getIva()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(cell);

            // TOTAL

            cell = new PdfPCell(new Phrase("TOTAL", totalFinal));
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(colorEmpresa);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(PdfUtils.formatImport(factura.getTotal()), totalFinal));
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(colorEmpresa);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(cell);

            // Espai

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.NO_BORDER);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(Rectangle.NO_BORDER);
            totals.addCell(cell);

            // Pagat

            cell = new PdfPCell(new Phrase("Pagat", fontPagat));
            cell.setBorder(Rectangle.NO_BORDER);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(PdfUtils.formatImport(importPagat), fontPagat));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(cell);

            // Pendent

            String etiquetaPendent = pendent.compareTo(BigDecimal.ZERO) < 0 ? "A retornar" : "Pendent";
            cell = new PdfPCell(new Phrase(etiquetaPendent, fontPendent));
            cell.setBorder(Rectangle.NO_BORDER);
            totals.addCell(cell);

            cell = new PdfPCell(new Phrase(PdfUtils.formatImport(pendent.abs()), fontPendent));
            cell.setBorder(Rectangle.NO_BORDER);
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