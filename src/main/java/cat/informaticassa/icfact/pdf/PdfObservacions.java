package cat.informaticassa.icfact.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;

public class PdfObservacions {

    public void afegir(Document document, String observacions) {
        try {
            if (observacions == null || observacions.isBlank()) {
                return;
            }
            Font titol = PdfFonts.normal();
            Font normal = PdfFonts.normal();
            Paragraph espai = new Paragraph();
            espai.setSpacingBefore(20f);
            document.add(espai);
            Paragraph pTitol = new Paragraph("OBSERVACIONS", titol);
            pTitol.setSpacingAfter(8f);
            document.add(pTitol);
            Paragraph pObservacions = new Paragraph(observacions, normal);
            pObservacions.setSpacingAfter(10f);
            document.add(pObservacions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}