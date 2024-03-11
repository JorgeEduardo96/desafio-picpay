package br.com.picpay.domain.service;


import br.com.picpay.api.model.input.TransferenciaInput;
import br.com.picpay.domain.enumeration.TipoTransferencia;
import br.com.picpay.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidadorTransferenciaService {

    private final ValidarUsuarioTipoTransferenciaService validarUsuarioTipoTransferenciaService;
    private final ValidarSaldoTransferencia validarSaldoTransferencia;

    public void execute(Usuario usuarioTransferencia, BigDecimal saldoUsuarioTransferencia, TransferenciaInput transferenciaInput) {
        validarUsuarioTipoTransferenciaService.isUsuarioAptoTipoTransferencia(usuarioTransferencia, TipoTransferencia.TRANSFERIR);
        validarSaldoTransferencia.validarSaldoUsuario(saldoUsuarioTransferencia, transferenciaInput.getValorTransferencia(), usuarioTransferencia.getNomeCompleto());
        log.info("As validações de isUsuarioAptoTipoTransferencia e validarSaldoUsuario foram relizadas com sucesso.");
    }

}
