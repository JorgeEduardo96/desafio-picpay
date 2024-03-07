package br.com.picpay.domain.model.exception;

public class OperacaoNaoAutorizadaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperacaoNaoAutorizadaException() {
        super("Operação não recebeu autorização de serviço externo.");
    }


}
