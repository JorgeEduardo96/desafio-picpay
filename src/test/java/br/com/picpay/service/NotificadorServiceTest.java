package br.com.picpay.service;

import br.com.picpay.api.client.NotificadorClient;
import br.com.picpay.api.model.dto.NotificadorResponseDto;
import br.com.picpay.domain.service.NotificadorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificadorServiceTest {

    @InjectMocks
    private NotificadorService notificadorService;

    @Mock
    private NotificadorClient notificadorClient;

    @Test
    void testSendNotificacaoTransferencia() {
        when(notificadorClient.sendNotificacaoTransferencia()).thenReturn(new NotificadorResponseDto(true));

        notificadorService.sendNotificacaoTransferencia();

        verify(notificadorClient, times(1)).sendNotificacaoTransferencia();
    }

}
