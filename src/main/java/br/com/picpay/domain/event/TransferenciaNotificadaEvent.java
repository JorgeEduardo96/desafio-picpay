package br.com.picpay.domain.event;

import br.com.picpay.domain.model.Transferencia;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransferenciaNotificadaEvent {

    private Transferencia transferencia;

}
