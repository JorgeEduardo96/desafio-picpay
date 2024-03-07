package br.com.picpay.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTransferencia {

    RECEBER("Receber"),
    TRANSFERIR("Transferir");

    private final String descricao;

}
