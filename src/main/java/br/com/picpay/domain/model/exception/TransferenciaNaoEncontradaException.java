package br.com.picpay.domain.model.exception;

import java.util.UUID;

public class TransferenciaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public TransferenciaNaoEncontradaException(UUID id) {
        super(String.format("Usuário de ID: %s, não existente.", id));
    }
}
