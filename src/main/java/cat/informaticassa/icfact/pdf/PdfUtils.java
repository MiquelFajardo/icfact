package cat.informaticassa.icfact.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;

import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class PdfUtils {

    private static final DateTimeFormatter FORMAT_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DecimalFormat FORMAT_IMPORT;

    static {

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        FORMAT_IMPORT = new DecimalFormat("#,##0.00", symbols);
    }

    private PdfUtils() {
    }

    public static String formatData(LocalDate data) {

        if (data == null) {
            return "";
        }

        return data.format(FORMAT_DATA);
    }

    public static String formatImport(BigDecimal valor) {

        if (valor == null) {
            return "0,00 €";
        }

        return FORMAT_IMPORT.format(valor) + " €";
    }

    public static PdfPCell crearCapcalera(String text, Color color) {

        PdfPCell cell = new PdfPCell(new Phrase(text, PdfFonts.normal()));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        cell.setBackgroundColor(color);

        cell.setBorder(Rectangle.NO_BORDER);

        cell.setPaddingTop(10);
        cell.setPaddingBottom(10);

        cell.setPhrase(new Phrase(text,
                new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE)));

        return cell;
    }

    public static PdfPCell crearCelda(String text,
                                      Font font,
                                      int align,
                                      Color background) {

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        cell.setBackgroundColor(background);

        cell.setPadding(8);

        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(Color.LIGHT_GRAY);

        return cell;
    }
}