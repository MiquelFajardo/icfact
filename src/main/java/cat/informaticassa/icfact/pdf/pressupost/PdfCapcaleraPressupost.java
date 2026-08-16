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

public class PdfCapcaleraPressupost {

    public void afegir(Document document, Pressupost pressupost, Empresa empresa) {

        try {
            PdfPTable capcalera = new PdfPTable(2);
            capcalera.setWidthPercentage(100);

            Color colorEmpresa = Color.decode(empresa.getColor());

            Font subtitol = new Font(Font.HELVETICA, 13);
            Font normal = PdfFonts.normal();

            // -------------------------
            // Esquerra
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
                    new Phrase(
                            "PRESSUPOST",
                            PdfFonts.titolGran(colorEmpresa)
                    )
            );

            textTitol.setBorder(Rectangle.NO_BORDER);
            textTitol.setVerticalAlignment(Element.ALIGN_MIDDLE);

            blocTitol.addCell(barra);
            blocTitol.addCell(textTitol);

            esquerra.addElement(blocTitol);

            PdfPTable dades = new PdfPTable(2);
            dades.setWidthPercentage(100);
            dades.setWidths(new float[]{30, 70});
            dades.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            dades.addCell(new Phrase(
                    "Número",
                    PdfFonts.etiqueta(colorEmpresa)
            ));

            dades.addCell(new Phrase(
                    pressupost.getNumero(),
                    subtitol
            ));

            dades.addCell(new Phrase(
                    "Data",
                    PdfFonts.etiqueta(colorEmpresa)
            ));

            dades.addCell(new Phrase(
                    PdfUtils.formatData(pressupost.getData()),
                    subtitol
            ));

            dades.addCell(new Phrase(
                    "Client",
                    PdfFonts.etiqueta(colorEmpresa)
            ));

            dades.addCell(new Phrase(
                    pressupost.getClient().getNom(),
                    subtitol
            ));

            esquerra.addElement(new Paragraph(" "));
            esquerra.addElement(dades);

            // -------------------------
            // Dreta
            // -------------------------

            PdfPCell dreta = new PdfPCell();
            dreta.setBorder(Rectangle.NO_BORDER);
            dreta.setHorizontalAlignment(Element.ALIGN_RIGHT);

            dreta.addElement(new Paragraph(
                    empresa.getNom(),
                    PdfFonts.nomEmpresa(colorEmpresa)
            ));

            dreta.addElement(new Paragraph(
                    empresa.getDescripcio() != null
                            ? empresa.getDescripcio()
                            : "",
                    normal
            ));

            PdfPTable dadesEmpresa = new PdfPTable(1);
            dadesEmpresa.setWidthPercentage(100);
            dadesEmpresa.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // -------------------------
            // Adreça
            // -------------------------

            if (empresa.getAdreca() != null) {

                dadesEmpresa.addCell(new Phrase(
                        empresa.getAdreca().getAdrecaCompleta(),
                        normal
                ));

                String codiPostal = "";
                String poblacio = "";

                if (empresa.getAdreca().getPoblacio() != null) {

                    codiPostal = empresa.getAdreca()
                            .getPoblacio()
                            .getCodiPostal()
                            .stream()
                            .findFirst()
                            .orElse("");

                    poblacio = empresa.getAdreca()
                            .getPoblacio()
                            .getNom();
                }

                String cpPoblacio = codiPostal;

                if (!poblacio.isBlank()) {

                    if (!cpPoblacio.isBlank()) {
                        cpPoblacio += " - ";
                    }

                    cpPoblacio += poblacio;
                }

                if (!cpPoblacio.isBlank()) {
                    dadesEmpresa.addCell(new Phrase(
                            cpPoblacio,
                            normal
                    ));
                }
            }

            // -------------------------
            // Contacte
            // -------------------------

            if (empresa.getTelefon() != null && !empresa.getTelefon().isBlank()) {
                dadesEmpresa.addCell(new Phrase(
                        empresa.getTelefon(),
                        normal
                ));
            }

            if (empresa.getWeb() != null && !empresa.getWeb().isBlank()) {
                dadesEmpresa.addCell(new Phrase(
                        empresa.getWeb(),
                        normal
                ));
            }

            if (empresa.getEmail() != null && !empresa.getEmail().isBlank()) {
                dadesEmpresa.addCell(new Phrase(
                        empresa.getEmail(),
                        normal
                ));
            }

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