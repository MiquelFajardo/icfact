package cat.informaticassa.icfact.ui.components.pagament;

import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pagament.service.RegistrarPagamentService;
import cat.informaticassa.icfact.ui.components.dialogs.PagamentDialog;
import cat.informaticassa.icfact.ui.util.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.nio.file.Path;

public class PagamentDialogEvents {
    private static final Logger logger = LoggerFactory.getLogger(PagamentDialogEvents.class);
    private final PagamentDialog dialog;
    private final RegistrarPagamentService registrarService = new RegistrarPagamentService();

    public PagamentDialogEvents(PagamentDialog dialog) {
        this.dialog = dialog;
        inicialitzar();
    }

    private void inicialitzar() {
        dialog.getBotoGuardar().setDisable(true);
        dialog.getDirtyTracker().modificatProperty().addListener(
                (obs, anterior, modificat) -> dialog.getBotoGuardar().setDisable(!modificat));
                dialog.getBotoGuardar().setOnAction(e -> guardar());
    }

    private void guardar() {
        try {
            BigDecimal importPagat = llegirImport();
            BigDecimal resta = dialog.getResta();
            if (importPagat.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("L'import del pagament ha de ser superior a zero.");
            }
            if (importPagat.compareTo(resta) > 0) {
                throw new IllegalArgumentException("L'import del pagament no pot ser superior a " + format(resta) + ".");
            }
            if (dialog.getFormulari().getDpData().getValue() == null) {
                throw new IllegalArgumentException("La data del pagament és obligatòria.");
            }
            Pagament pagament = getPagament(importPagat);
            Path pdfActualitzat = registrarService.executar(pagament);
            dialog.getDirtyTracker().marcarDesat();
            if (pdfActualitzat != null) {
                boolean obrir = Alerta.confirmar(dialog,"Factura actualitzada","La factura PDF ha estat actualitzada.\nVols obrir-la?");
                dialog.close();
                if (obrir) {
                    registrarService.obrirPdf(pdfActualitzat);
                }
            } else {
                dialog.close();
            }
        } catch (IllegalArgumentException ex) {
            Alerta.error(dialog, ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error inesperat registrant un pagament.", ex);
            Alerta.error(dialog,"No s'ha pogut registrar el pagament.");
        }
    }

    private Pagament getPagament(BigDecimal importPagat) {
        Pagament pagament = dialog.getPagament();
        pagament.setImportPagat(importPagat);
        pagament.setDataPagament(dialog.getFormulari().getDpData().getValue());
        pagament.setReferencia(dialog.getFormulari().getTxtReferencia().getText().trim());
        pagament.setObservacions(dialog.getFormulari().getTxtObservacions().getText().trim());
        return pagament;
    }

    private BigDecimal llegirImport() {
        String text = dialog.getFormulari().getTxtImport().getText().trim().replace(",", ".");
        if (text.isBlank()) {
            throw new IllegalArgumentException("Has d'indicar l'import del pagament.");
        }
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("L'import del pagament no és vàlid.");
        }
    }

    private String format(BigDecimal valor) {
        return String.format(java.util.Locale.forLanguageTag("ca-ES"),"%.2f €",valor);
    }
}