package br.com.picpay.domain.service;

import br.com.picpay.api.client.NotificadorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificadorService {

    private final NotificadorClient notificadorClient;

    public void sendNotificacaoTransferencia() {
        notificadorClient.sendNotificacaoTransferencia();
    }


}
