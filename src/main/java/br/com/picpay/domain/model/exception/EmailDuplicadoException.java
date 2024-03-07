package br.com.picpay.domain.model.exception;

public class EmailDuplicadoException extends EntidadeDuplicadaException {

    private static final long serialVersionUID = 1L;

    public EmailDuplicadoException(String email) {
        super(String.format("O e-mail '%s' já está cadastrado!", email));
    }

}
