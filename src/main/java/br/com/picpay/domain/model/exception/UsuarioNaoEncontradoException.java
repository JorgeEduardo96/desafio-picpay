package br.com.picpay.domain.model.exception;

import java.util.UUID;

public class UsuarioNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(UUID id) {
        super(String.format("Usuário de ID: %s, não existente.", id));
    }
}
