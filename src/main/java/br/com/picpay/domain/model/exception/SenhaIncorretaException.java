package br.com.picpay.domain.model.exception;

public class SenhaIncorretaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SenhaIncorretaException(String msg) {
        super(msg);
    }

}
