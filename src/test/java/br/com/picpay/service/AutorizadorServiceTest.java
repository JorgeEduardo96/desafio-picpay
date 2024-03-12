package br.com.picpay.service;

import br.com.picpay.api.client.AutorizadorClient;
import br.com.picpay.api.model.dto.AutorizadorResponseDto;
import br.com.picpay.domain.service.AutorizadorService;
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
public class AutorizadorServiceTest {

    private final static String AUTORIZADO = "Autorizado";

    @InjectMocks
    private AutorizadorService autorizadorService;

    @Mock
    private AutorizadorClient autorizadorClient;

    @Test
    void testIsAutorizado() {
        Logger logger = (Logger) LoggerFactory.getLogger(AutorizadorService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        when(autorizadorClient.isAutorizado()).thenReturn(new AutorizadorResponseDto(AUTORIZADO));

        autorizadorService.isAutorizado();
        List<ILoggingEvent> logsList = listAppender.list;

        verify(autorizadorClient, times(1)).isAutorizado();
        assertNotNull(logsList);
        assertEquals(Level.INFO, logsList.get(0).getLevel());
        assertEquals("Autorização de serviço externo feita com sucesso.", logsList.get(0).getMessage());
    }



}
