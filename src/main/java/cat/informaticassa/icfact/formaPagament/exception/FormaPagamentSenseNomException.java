package cat.informaticassa.icfact.formaPagament.exception;

public class FormaPagamentSenseNomException extends RuntimeException {

    public FormaPagamentSenseNomException() {
        super("El nom de la forma de pagament és obligatori.");
    }
}