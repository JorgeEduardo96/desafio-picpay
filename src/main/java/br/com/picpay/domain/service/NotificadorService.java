package br.com.picpay.domain.service;

import br.com.picpay.api.client.NotificadorClient;
import br.com.picpay.domain.model.Transferencia;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificadorService {

    private final NotificadorClient notificadorClient;
    private final CadastroTransferenciaService cadastroTransferenciaService;

    @CircuitBreaker(name = "customCB", fallbackMethod = "fallbackSendNotificacaoTransferencia")
    public void sendNotificacaoTransferencia(Transferencia transferencia) {
        notificadorClient.sendNotificacaoTransferencia();
        cadastroTransferenciaService.confirmacaoNotifacaoTransferencia(transferencia.getId());
        log.info("Notificação enviada com sucesso para o cliente {}", transferencia.getUsuarioRecebimento().getNomeCompleto());
    }

    private void fallbackSendNotificacaoTransferencia(Transferencia transferencia, Exception ex) {
        log.warn("Notificação da transferência de ID: {} não enviada.", transferencia.getId());
        log.warn("Maiores detalhes: {}", ex.getMessage());
    }

    // a cada 5 minutos procura por transferências sem notificação para serem reprocessadas
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    protected void sendNotificacaoTransferencias() {
        List<Transferencia> transferenciasNaoNotificadas = cadastroTransferenciaService.getAllTransferenciaNaoNotificadas();
        if (transferenciasNaoNotificadas.isEmpty()) return;
        log.info("Foram encontrados {} transferências sem notificação, entrando no fluxo de reenvio de notificação!",
                transferenciasNaoNotificadas.size());
        for(Transferencia transferencia: transferenciasNaoNotificadas) {
            this.sendNotificacaoTransferencia(transferencia);
        }
    }

}
