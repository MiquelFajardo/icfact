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

public class PdfCapcaleraFactura {

    public void afegir(Document document, Factura factura, Empresa empresa) {

        try {

            PdfPTable capcalera = new PdfPTable(2);
            capcalera.setWidthPercentage(100);
            capcalera.setWidths(new float[]{50, 50});

            Color colorEmpresa = Color.decode(empresa.getColor());

            Font subtitol = new Font(Font.HELVETICA, 13);
            Font normal = PdfFonts.normal();

            // -------------------------
            // ESQUERRA
            // -------------------------

            PdfPTable blocTitol = new PdfPTable(2);
            blocTitol.setWidthPercentage(100);
            blocTitol.setWidths(new float[]{2, 98});

            PdfPCell esquerra = new PdfPCell();
            esquerra.setBorder(Rectangle.NO_BORDER);

            PdfPCell barra = new PdfPCell();
            barra.setBorder(Rectangle.NO_BORDER);
            barra.setBackgroundColor(colorEmpresa);
            barra.setFixedHeight(35f);

            PdfPCell textTitol = new PdfPCell(
                    new Phrase("FACTURA", PdfFonts.titolGran(colorEmpresa)));

            textTitol.setBorder(Rectangle.NO_BORDER);
            textTitol.setVerticalAlignment(Element.ALIGN_MIDDLE);

            blocTitol.addCell(barra);
            blocTitol.addCell(textTitol);

            esquerra.addElement(blocTitol);
            esquerra.addElement(new Paragraph(" "));

            PdfPTable dades = new PdfPTable(2);
            dades.setWidthPercentage(100);
            dades.setWidths(new float[]{30, 70});
            dades.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            dades.addCell(new Phrase("Número", PdfFonts.etiqueta(colorEmpresa)));
            dades.addCell(new Phrase(factura.getNumero(), subtitol));

            dades.addCell(new Phrase("Data", PdfFonts.etiqueta(colorEmpresa)));
            dades.addCell(new Phrase(
                    PdfUtils.formatData(factura.getData()),
                    subtitol));

            dades.addCell(new Phrase("Client", PdfFonts.etiqueta(colorEmpresa)));
            dades.addCell(new Phrase(
                    factura.getClient().getNom(),
                    subtitol));

            dades.addCell(new Phrase("NIF", PdfFonts.etiqueta(colorEmpresa)));
            dades.addCell(new Phrase(
                    factura.getClient().getNif(),
                    subtitol));

            dades.addCell(new Phrase("Adreça", PdfFonts.etiqueta(colorEmpresa)));
            dades.addCell(new Phrase(
                    factura.getClient().getAdreca().getAdrecaCompleta(),
                    normal));

            dades.addCell(new Phrase("CP", PdfFonts.etiqueta(colorEmpresa)));
            dades.addCell(new Phrase(
                    factura.getClient().getAdreca().getPoblacio().getCodiPostal()
                            + " - "
                            + factura.getClient().getAdreca().getPoblacio().getNom(),
                    normal));

            if (factura.getClient().getTelefon() != null
                    && !factura.getClient().getTelefon().isBlank()) {

                dades.addCell(new Phrase("Telèfon", PdfFonts.etiqueta(colorEmpresa)));
                dades.addCell(new Phrase(
                        factura.getClient().getTelefon(),
                        normal));
            }

            esquerra.addElement(dades);

            // -------------------------
            // DRETA
            // -------------------------

            PdfPCell dreta = new PdfPCell();
            dreta.setBorder(Rectangle.NO_BORDER);
            dreta.setHorizontalAlignment(Element.ALIGN_RIGHT);

            dreta.addElement(new Paragraph(empresa.getNom(), PdfFonts.nomEmpresa(colorEmpresa)));

            dreta.addElement(new Paragraph(empresa.getDescripcio(), normal));

            PdfPTable dadesEmpresa = new PdfPTable(1);
            dadesEmpresa.setWidthPercentage(100);
            dadesEmpresa.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            dadesEmpresa.addCell(new Phrase("NIF: " + empresa.getNif(), normal));

            dadesEmpresa.addCell(new Phrase(empresa.getAdreca().getAdrecaCompleta(), normal));

            dadesEmpresa.addCell(new Phrase(empresa.getAdreca().getPoblacio().getCodiPostal() + " - "
                            + empresa.getAdreca().getPoblacio().getNom(), normal));

            dadesEmpresa.addCell(new Phrase(empresa.getTelefon(), normal));

            dadesEmpresa.addCell(new Phrase(empresa.getEmail(), normal));

            dreta.addElement(dadesEmpresa);

            // -------------------------

            capcalera.addCell(esquerra);
            capcalera.addCell(dreta);

            document.add(capcalera);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}