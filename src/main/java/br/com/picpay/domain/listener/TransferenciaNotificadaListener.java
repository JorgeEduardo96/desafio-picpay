package br.com.picpay.domain.listener;

import br.com.picpay.domain.event.TransferenciaNotificadaEvent;
import br.com.picpay.domain.service.NotificadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class TransferenciaNotificadaListener {

    private final NotificadorService notificadorService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void afterTransferenciaRealizada(TransferenciaNotificadaEvent event) {
        notificadorService.sendNotificacaoTransferencia(event.getTransferencia());
    }

}
