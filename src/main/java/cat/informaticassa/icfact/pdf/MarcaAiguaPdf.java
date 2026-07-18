package cat.informaticassa.icfact.pdf;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;

public class MarcaAiguaPdf {

    public void dibuixar(PdfWriter writer, String text) {

        if (text == null || text.isBlank()) {
            return;
        }

        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont font = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.90f);
            cb.saveState();
            cb.setGState(gs);
            cb.beginText();
            cb.setFontAndSize(font, 35);
            cb.setColorFill(new Color(220, 220, 220));
            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, text, 300f,200f,25);
            cb.endText();
            cb.restoreState();
            cb.restoreState();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}