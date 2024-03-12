package br.com.picpay.service;

import br.com.picpay.api.client.NotificadorClient;
import br.com.picpay.api.model.dto.NotificadorResponseDto;
import br.com.picpay.domain.model.Transferencia;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.service.CadastroTransferenciaService;
import br.com.picpay.domain.service.NotificadorService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificadorServiceTest {

    @InjectMocks
    private NotificadorService notificadorService;

    @Mock
    private NotificadorClient notificadorClient;

    @Mock
    private CadastroTransferenciaService cadastroTransferenciaService;

    @Test
    void testSendNotificacaoTransferencia() {
        Logger logger = (Logger) LoggerFactory.getLogger(NotificadorService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        Transferencia transferenciaMock = Transferencia.builder()
                .usuarioRecebimento(Usuario.builder().nomeCompleto("Usuário Teste").build())
                .build();

        when(notificadorClient.sendNotificacaoTransferencia()).thenReturn(new NotificadorResponseDto(true));
        doNothing().when(cadastroTransferenciaService).confirmacaoNotifacaoTransferencia(any());

        notificadorService.sendNotificacaoTransferencia(transferenciaMock);
        List<ILoggingEvent> logsList = listAppender.list;

        verify(notificadorClient, times(1)).sendNotificacaoTransferencia();
        assertNotNull(logsList);
        assertEquals(Level.INFO, logsList.get(0).getLevel());
        assertEquals("Notificação enviada com sucesso para o cliente {}", logsList.get(0).getMessage());
    }

}
