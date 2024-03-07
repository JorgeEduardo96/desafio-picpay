package br.com.picpay.service;

import br.com.picpay.domain.model.exception.SaldoInsuficienteException;
import br.com.picpay.domain.model.exception.SenhaIncorretaException;
import br.com.picpay.domain.service.ValidarSaldoTransferencia;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ValidarSaldoTransferenciaServiceTest {

    @InjectMocks
    private ValidarSaldoTransferencia validarSaldoTransferencia;

    @Test
    void testValidarSaldoUsuario_whenSaldoInsuficiente_throwsException() {
        BigDecimal saldo = BigDecimal.ZERO;
        BigDecimal valorTransferencia = BigDecimal.TEN;
        String nomeCompletoUsuario = "Usuário Teste";

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> {
            validarSaldoTransferencia.validarSaldoUsuario(saldo, valorTransferencia, nomeCompletoUsuario);
        });

        assertEquals("Usuário(a) Usuário Teste não tem saldo suficiente para realizar a transferência.", exception.getMessage());
    }

}
