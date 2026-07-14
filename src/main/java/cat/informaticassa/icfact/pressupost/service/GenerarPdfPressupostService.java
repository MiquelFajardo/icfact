package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.empresa.repository.EmpresaRepository;
import cat.informaticassa.icfact.pressupost.exception.PressupostNoExisteixException;
import cat.informaticassa.icfact.pressupost.model.LiniaPressupost;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import cat.informaticassa.icfact.pressupost.repository.PressupostRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;

public class GenerarPdfPressupostService {

    private final PressupostRepository repository = new PressupostRepository();
    private final EmpresaRepository empresaRepository = new EmpresaRepository();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Path executar(Long pressupostId) {
        Empresa empresa = empresaRepository.buscar()
                .orElseThrow(() -> new RuntimeException("No existeix cap empresa."));

        Pressupost pressupost = repository.buscarPerIdAmbLinies(pressupostId)
                .orElseThrow(() ->
                        new PressupostNoExisteixException("El pressupost no existeix."));

        Path carpeta = Path.of("pressupostos");

        try {
            Files.createDirectories(carpeta);
        } catch (IOException e) {
            throw new RuntimeException("No s'ha pogut crear la carpeta de pressupostos.", e);
        }

        Path fitxer = carpeta.resolve(pressupost.getNumero() + ".pdf");

        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            DecimalFormat format = new DecimalFormat("#,##0.00", symbols);

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(fitxer.toFile()));
            document.open();

            // Capçalera
            PdfPTable capcalera = new PdfPTable(2);
            capcalera.setWidthPercentage(100);

            Color colorEmpresa = Color.decode(empresa.getColor());
            Font normal = new Font(Font.HELVETICA, 11);

            // Client
            PdfPTable blocTitol = new PdfPTable(2);
            blocTitol.setWidthPercentage(100);
            blocTitol.setWidths(new float[]{2, 98});
            PdfPCell esquerra = new PdfPCell();
            esquerra.setBorder(Rectangle.NO_BORDER);
            PdfPCell barra = new PdfPCell();
            barra.setBorder(Rectangle.NO_BORDER);
            barra.setBackgroundColor(colorEmpresa);
            barra.setFixedHeight(35f);

            Font titolGran = new Font(Font.HELVETICA, 26, Font.BOLD, colorEmpresa);
            PdfPCell textTitol = new PdfPCell(new Phrase("PRESSUPOST", titolGran));
            textTitol.setBorder(Rectangle.NO_BORDER);
            textTitol.setVerticalAlignment(Element.ALIGN_MIDDLE);
            blocTitol.addCell(barra);
            blocTitol.addCell(textTitol);
            esquerra.addElement(blocTitol);

            Font etiqueta = new Font(Font.HELVETICA, 11, Font.BOLD, colorEmpresa);
            PdfPTable dades = new PdfPTable(2);
            dades.setWidthPercentage(100);
            dades.setWidths(new float[]{30, 70});
            dades.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            dades.addCell(new Phrase("Número", etiqueta));
            dades.addCell(new Phrase(pressupost.getNumero(), normal));
            dades.addCell(new Phrase("Data", etiqueta));
            dades.addCell(new Phrase(pressupost.getData().format(formatter), normal));
            dades.addCell(new Phrase("Client", etiqueta));
            dades.addCell(new Phrase(pressupost.getClient().getNom(), normal));

            esquerra.addElement(new Paragraph(" "));
            esquerra.addElement(dades);

            PdfPCell dreta = new PdfPCell();
            dreta.setBorder(Rectangle.NO_BORDER);
            dreta.setHorizontalAlignment(Element.ALIGN_RIGHT);

            // Empresa
            Font nomEmpresa = new Font(Font.HELVETICA, 22, Font.BOLD, colorEmpresa);

            dreta.addElement(new Paragraph(empresa.getNom(), nomEmpresa));
            dreta.addElement(new Paragraph(empresa.getDescripcio(), normal));


            PdfPTable dadesEmpresa = new PdfPTable(1);
            dadesEmpresa.setWidthPercentage(100);
            dadesEmpresa.getDefaultCell().setBorder(Rectangle.NO_BORDER);

             dadesEmpresa.addCell(new Phrase(empresa.getAdreca().getAdrecaCompleta(), normal));
            dadesEmpresa.addCell(new Phrase(
                    empresa.getAdreca().getPoblacio().getCodiPostal() + " - " +
                          empresa.getAdreca().getPoblacio().getNom(), normal));
            dadesEmpresa.addCell(new Phrase(empresa.getTelefon(), normal));
            dadesEmpresa.addCell(new Phrase(empresa.getWeb(), normal));
            dadesEmpresa.addCell(new Phrase(empresa.getEmail(), normal));

            dreta.addElement(dadesEmpresa);
            capcalera.addCell(esquerra);
            capcalera.addCell(dreta);
            document.add(capcalera);
            Paragraph espai = new Paragraph();
            espai.setSpacingAfter(20f);
            document.add(espai);


            // Pressupost
            PdfPTable taula = new PdfPTable(5);

            // Capçalera
            taula.setWidthPercentage(100);
            taula.setWidths(new float[]{45, 12, 15, 10, 18});
            Font capcaleraFont = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);
            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Descripció", capcaleraFont));
            cell.setBackgroundColor(colorEmpresa);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(10);
            cell.setBorderWidthBottom(1f);
            cell.setBorderColorBottom(colorEmpresa.darker());
            taula.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantitat", capcaleraFont));
            cell.setBackgroundColor(colorEmpresa);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6);
            cell.setBorderWidthBottom(1f);
            cell.setBorderColorBottom(colorEmpresa.darker());
            taula.addCell(cell);

            cell = new PdfPCell(new Phrase("Preu", capcaleraFont));
            cell.setBackgroundColor(colorEmpresa);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            cell.setBorderWidthBottom(1f);
            cell.setBorderColorBottom(colorEmpresa.darker());
            taula.addCell(cell);

            cell = new PdfPCell(new Phrase("IVA", capcaleraFont));
            cell.setBackgroundColor(colorEmpresa);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            cell.setBorderWidthBottom(1f);
            cell.setBorderColorBottom(colorEmpresa.darker());
            taula.addCell(cell);

            cell = new PdfPCell(new Phrase("Total", capcaleraFont));
            cell.setBackgroundColor(colorEmpresa);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            cell.setBorderWidthBottom(1f);
            cell.setBorderColorBottom(colorEmpresa.darker());
            taula.addCell(cell);

            // Cos pressupost
            for (LiniaPressupost l : pressupost.getLinies()) {

                PdfPCell cella;

                cella = new PdfPCell(new Phrase(l.getDescripcio(), normal));
                cella.setPadding(8);
                cella.setBorder(Rectangle.BOTTOM);
                cella.setBorderColor(Color.LIGHT_GRAY);
                taula.addCell(cella);

                cella = new PdfPCell(new Phrase(l.getQuantitat().toString(), normal));
                cella.setHorizontalAlignment(Element.ALIGN_CENTER);
                cella.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cella.setPadding(8);
                cella.setBorder(Rectangle.BOTTOM);
                cella.setBorderColor(Color.LIGHT_GRAY);
                taula.addCell(cella);

                cella = new PdfPCell(new Phrase(format.format(l.getPreu()) + " €", normal));
                cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cella.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cella.setPadding(8);
                cella.setBorder(Rectangle.BOTTOM);
                cella.setBorderColor(Color.LIGHT_GRAY);
                taula.addCell(cella);

                cella = new PdfPCell(new Phrase(l.getIva().getPercentatge().toString() + "%", normal));
                cella.setHorizontalAlignment(Element.ALIGN_CENTER);
                cella.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cella.setPadding(8);
                cella.setBorder(Rectangle.BOTTOM);
                cella.setBorderColor(Color.LIGHT_GRAY);
                taula.addCell(cella);

                cella = new PdfPCell(new Phrase(format.format(l.getTotal()) + " €", normal));
                cella.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cella.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cella.setPadding(8);
                cella.setBorder(Rectangle.BOTTOM);
                cella.setBorderColor(Color.LIGHT_GRAY);
                taula.addCell(cella);
            }
            document.add(taula);


            // Totals
            PdfPTable totals = new PdfPTable(2);
            totals.setWidthPercentage(35);
            totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.setWidths(new float[]{55, 45});

            Font etiquetaTotals = new Font(Font.HELVETICA, 11, Font.BOLD);
            Font totalFinal = new Font(Font.HELVETICA, 13, Font.BOLD, colorEmpresa);

            PdfPCell c;

            c = new PdfPCell(new Phrase("Subtotal", etiquetaTotals));
            c.setBorder(Rectangle.NO_BORDER);
            totals.addCell(c);

            c = new PdfPCell(new Phrase(format.format(pressupost.getSubtotal())+ " €", normal));
            c.setBorder(Rectangle.NO_BORDER);
            c.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(c);

            c = new PdfPCell(new Phrase("IVA", etiquetaTotals));
            c.setBorder(Rectangle.NO_BORDER);
            totals.addCell(c);

            c = new PdfPCell(new Phrase(format.format(pressupost.getIva()) + " €", normal));
            c.setBorder(Rectangle.NO_BORDER);
            c.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(c);

            c = new PdfPCell(new Phrase("TOTAL", totalFinal));
            c.setBorder(Rectangle.TOP);
            c.setBorderColor(colorEmpresa);
            totals.addCell(c);

            c = new PdfPCell(new Phrase(format.format(pressupost.getTotal()) + " €", totalFinal));
            c.setBorder(Rectangle.TOP);
            c.setBorderColor(colorEmpresa);
            c.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totals.addCell(c);

            espai.setSpacingAfter(15f);
            document.add(espai);
            document.add(totals);

            document.close();
            return fitxer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}