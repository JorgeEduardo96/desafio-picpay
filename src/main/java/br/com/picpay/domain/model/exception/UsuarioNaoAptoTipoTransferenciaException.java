package br.com.picpay.domain.model.exception;

import br.com.picpay.domain.enumeration.TipoTransferencia;

public class UsuarioNaoAptoTipoTransferenciaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoAptoTipoTransferenciaException(String nomeCompleto, TipoTransferencia tipoTransferenciaUsuario) {
        super(String.format("%s não está apto para transferências do tipo: %s", nomeCompleto, tipoTransferenciaUsuario.getDescricao()));
    }
}
