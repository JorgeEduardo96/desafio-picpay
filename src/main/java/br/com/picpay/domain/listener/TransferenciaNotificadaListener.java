package br.com.picpay.domain.listener;

import br.com.picpay.domain.event.TransferenciaNotificadaEvent;
import br.com.picpay.domain.service.NotificadorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransferenciaNotificadaListener {

    private final NotificadorService notificadorService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterTransferenciaRealizada(TransferenciaNotificadaEvent event) {
        notificadorService.sendNotificacaoTransferencia();
        log.info("Notificação enviada com sucesso para o cliente {}", event.getTransferencia().getUsuarioRecebimento().getNomeCompleto());
    }

}
