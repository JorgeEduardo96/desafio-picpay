package br.com.picpay.domain.service;

import br.com.picpay.api.model.dto.UsuarioDto;
import br.com.picpay.domain.model.exception.SaldoInsuficienteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidarSaldoTransferencia {

    public void validarSaldoUsuario(BigDecimal saldo, BigDecimal valorTransferencia, String nomeCompleto) {
        if (saldo.compareTo(valorTransferencia) < 0) throw new SaldoInsuficienteException(nomeCompleto);
    }

}
