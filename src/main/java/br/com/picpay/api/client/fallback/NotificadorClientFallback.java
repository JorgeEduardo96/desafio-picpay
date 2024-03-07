package br.com.picpay.api.client.fallback;

import br.com.picpay.api.client.NotificadorClient;
import br.com.picpay.api.model.dto.NotificadorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificadorClientFallback implements NotificadorClient {

    @Override
    public NotificadorResponseDto sendNotificacaoTransferencia() {
        log.warn("Fallback: Notificação não pôde ser enviada.");
        return new NotificadorResponseDto(false);
    }
}
