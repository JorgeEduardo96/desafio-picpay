package br.com.picpay.api.client;

import br.com.picpay.api.model.dto.AutorizadorResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "autorizador-client", url = "${autorizador.url}")
public interface AutorizadorClient {

    @GetMapping("/5794d450-d2e2-4412-8131-73d0293ac1cc")
    AutorizadorResponseDto isAutorizado();

}
