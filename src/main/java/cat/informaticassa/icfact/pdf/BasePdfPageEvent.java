package cat.informaticassa.icfact.pdf;

import cat.informaticassa.icfact.empresa.model.Empresa;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.*;

import java.awt.*;

public class BasePdfPageEvent extends PdfPageEventHelper {

    protected static final float Y_LINIA = 40f;
    protected static final float Y_TEXT = 25f;
    protected PdfTemplate totalPagines;
    protected BaseFont baseFont;

    protected final Empresa empresa;

    public BasePdfPageEvent(Empresa empresa) {
        this.empresa = empresa;
    }


    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        totalPagines = writer.getDirectContent().createTemplate(40, 12);
        try {
            baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Color color = Color.decode(empresa.getColor());
        cb.setColorStroke(color);
        cb.setLineWidth(1f);
        cb.moveTo(document.left(), Y_LINIA);
        cb.lineTo(document.right(), Y_LINIA);
        cb.stroke();
        Font font = PdfFonts.peuPagina();
        Phrase esquerra = new Phrase(empresa.getNom() + " · " + empresa.getWeb() + " · " + empresa.getEmail(), font);
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, esquerra, document.left(), Y_TEXT, 0);
        String text = "Pàgina " + writer.getPageNumber() + " de ";
        float x = document.right() - 55;
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, new Phrase(text, font), x, Y_TEXT,0);
        cb.addTemplate(totalPagines,x + baseFont.getWidthPoint(text, 9), Y_TEXT);
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        totalPagines.beginText();
        totalPagines.setFontAndSize(baseFont, 9);
        totalPagines.setTextMatrix(0, 0);
        totalPagines.showText(String.valueOf(writer.getPageNumber() - 1));
        totalPagines.endText();
    }
}