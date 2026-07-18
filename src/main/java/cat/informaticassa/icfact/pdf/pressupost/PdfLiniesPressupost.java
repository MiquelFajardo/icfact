package cat.informaticassa.icfact.pdf.pressupost;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.pdf.PdfFonts;
import cat.informaticassa.icfact.pdf.PdfUtils;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;

public class PdfLiniesPressupost {

    public void afegir(Document document,
                       Pressupost pressupost,
                       Empresa empresa) {

        try {

            Color colorEmpresa = Color.decode(empresa.getColor());

            Font normal = PdfFonts.normal();

            Paragraph espai = new Paragraph();
            espai.setSpacingAfter(20f);
            document.add(espai);

            PdfPTable taula = new PdfPTable(5);
            taula.setWidthPercentage(100);
            taula.setWidths(new float[]{39, 17, 15, 12, 17});

            taula.addCell(PdfUtils.crearCapcalera("Descripció", colorEmpresa));
            taula.addCell(PdfUtils.crearCapcalera("Quantitat", colorEmpresa));
            taula.addCell(PdfUtils.crearCapcalera("Preu", colorEmpresa));
            taula.addCell(PdfUtils.crearCapcalera("IVA", colorEmpresa));
            taula.addCell(PdfUtils.crearCapcalera("Total", colorEmpresa));

            taula.setHeaderRows(1);

            boolean gris = false;

            for (LiniaPressupost linia : pressupost.getLinies()) {

                Color fons = gris
                        ? new Color(245, 245, 245)
                        : Color.WHITE;

                taula.addCell(PdfUtils.crearCelda(
                        linia.getDescripcio(),
                        normal,
                        Element.ALIGN_LEFT,
                        fons));

                taula.addCell(PdfUtils.crearCelda(
                        linia.getQuantitat().toString(),
                        normal,
                        Element.ALIGN_CENTER,
                        fons));

                taula.addCell(PdfUtils.crearCelda(
                        PdfUtils.formatImport(linia.getPreu()),
                        normal,
                        Element.ALIGN_RIGHT,
                        fons));

                taula.addCell(PdfUtils.crearCelda(
                        linia.getIva().getPercentatge() + "%",
                        normal,
                        Element.ALIGN_CENTER,
                        fons));

                taula.addCell(PdfUtils.crearCelda(
                        PdfUtils.formatImport(linia.getTotal()),
                        normal,
                        Element.ALIGN_RIGHT,
                        fons));

                gris = !gris;
            }

            document.add(taula);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}