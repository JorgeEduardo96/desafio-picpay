package br.com.picpay.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TipoUsuario {

    COMUM("Comum", List.of(TipoTransferencia.RECEBER, TipoTransferencia.TRANSFERIR)),
    LOJISTA("Lojista", List.of(TipoTransferencia.RECEBER));

    private final String descricao;
    private final List<TipoTransferencia> tiposTransferenciaPermitidas;


}
