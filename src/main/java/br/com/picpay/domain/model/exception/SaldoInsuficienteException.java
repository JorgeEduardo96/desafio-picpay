package br.com.picpay.domain.model.exception;

public class SaldoInsuficienteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SaldoInsuficienteException(String nomeCompleto) {
        super(String.format("Usuário(a) %s não tem saldo suficiente para realizar a transferência.", nomeCompleto));
    }

}
