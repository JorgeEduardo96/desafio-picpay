package br.com.picpay.domain.model.exception;

public class CpfCnpjDuplicadoException extends EntidadeDuplicadaException {

    private static final long serialVersionUID = 1L;

    public CpfCnpjDuplicadoException(String cpfCnpj) {
        super(String.format("O CPF/CNPJ '%s' já está cadastrado!", cpfCnpj));
    }

}
