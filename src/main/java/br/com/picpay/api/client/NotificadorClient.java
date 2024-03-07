package br.com.picpay.api.client;

import br.com.picpay.api.client.fallback.NotificadorClientFallback;
import br.com.picpay.api.model.dto.NotificadorResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "notificador-client", url = "${notificador.url}", fallback = NotificadorClientFallback.class)
public interface NotificadorClient {

    @GetMapping("/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
    NotificadorResponseDto sendNotificacaoTransferencia();

}
