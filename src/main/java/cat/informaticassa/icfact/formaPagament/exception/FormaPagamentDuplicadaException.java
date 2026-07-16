package cat.informaticassa.icfact.formaPagament.exception;

public class FormaPagamentDuplicadaException extends RuntimeException {

    public FormaPagamentDuplicadaException() {
        super("Ja existeix una forma de pagament amb aquest nom.");
    }
}