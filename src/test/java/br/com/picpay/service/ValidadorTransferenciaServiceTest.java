package br.com.picpay.service;


import br.com.picpay.api.model.input.TransferenciaInput;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.service.ValidadorTransferenciaService;
import br.com.picpay.domain.service.ValidarSaldoTransferencia;
import br.com.picpay.domain.service.ValidarUsuarioTipoTransferenciaService;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidadorTransferenciaServiceTest {


    @InjectMocks
    private ValidadorTransferenciaService validadorTransferenciaService;

    @Mock
    private ValidarUsuarioTipoTransferenciaService validarUsuarioTipoTransferenciaService;
    @Mock
    private ValidarSaldoTransferencia validarSaldoTransferencia;

    @Test
    void testExecute() {
        Usuario usuario = Usuario.builder().nomeCompleto("Usuário Teste").build();
        BigDecimal saldoUsuarioTransferencia = BigDecimal.TEN;
        TransferenciaInput transferenciaInput = new TransferenciaInput();
        transferenciaInput.setValorTransferencia(BigDecimal.ONE);

        Logger logger = (Logger) LoggerFactory.getLogger(ValidadorTransferenciaService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        doNothing().when(validarUsuarioTipoTransferenciaService).isUsuarioAptoTipoTransferencia(any(), any());
        doNothing().when(validarSaldoTransferencia).validarSaldoUsuario(any(), any(), any());

        validadorTransferenciaService.execute(usuario, saldoUsuarioTransferencia, transferenciaInput);
        List<ILoggingEvent> logsList = listAppender.list;

        verify(validarUsuarioTipoTransferenciaService, times(1)).isUsuarioAptoTipoTransferencia(any(), any());
        verify(validarSaldoTransferencia, times(1)).validarSaldoUsuario(any(), any(), any());
        assertNotNull(logsList);
        assertEquals(Level.INFO, logsList.get(0).getLevel());
        assertEquals("As validações de isUsuarioAptoTipoTransferencia e validarSaldoUsuario foram relizadas com sucesso.",
                logsList.get(0).getMessage());
    }

}
