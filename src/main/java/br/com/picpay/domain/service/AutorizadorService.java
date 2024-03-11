package br.com.picpay.domain.service;

import br.com.picpay.api.client.AutorizadorClient;
import br.com.picpay.domain.model.exception.OperacaoNaoAutorizadaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutorizadorService {

    private final static String AUTORIZADO = "Autorizado";

    private final AutorizadorClient autorizadorClient;

    public void isAutorizado() {
        if (!AUTORIZADO.equalsIgnoreCase(autorizadorClient.isAutorizado().getMessage())) throw new OperacaoNaoAutorizadaException();
        log.info("Autorização de serviço externo feita com sucesso.");
    }

}
