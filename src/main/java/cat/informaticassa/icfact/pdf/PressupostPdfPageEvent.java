package cat.informaticassa.icfact.pdf;

import cat.informaticassa.icfact.empresa.model.Empresa;

public class PressupostPdfPageEvent extends BasePdfPageEvent {

    public PressupostPdfPageEvent(Empresa empresa) {
        super(empresa);
    }
}