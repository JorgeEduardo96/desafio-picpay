package br.com.picpay.domain.model.exception;

public class EntidadeDuplicadaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntidadeDuplicadaException(String mensagem) {
        super(mensagem);
    }

    public EntidadeDuplicadaException(String message, Throwable cause) {
        super(message, cause);
    }

}
