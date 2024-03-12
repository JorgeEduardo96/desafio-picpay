package br.com.picpay.service;


import br.com.picpay.api.model.input.UsuarioInput;
import br.com.picpay.domain.service.ValidadorCadastroUsuarioService;
import br.com.picpay.domain.service.ValidarCpfCnpjUnicoService;
import br.com.picpay.domain.service.ValidarEmailUnicoService;
import br.com.picpay.domain.service.ValidarSenhaUsuarioService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidadorCadastroUsuarioServiceTest {


    @InjectMocks
    private ValidadorCadastroUsuarioService validadorCadastroUsuarioService;

    @Mock
    private ValidarEmailUnicoService validarEmailUnicoService;
    @Mock
    private ValidarCpfCnpjUnicoService validarCpfCnpjUnicoService;
    @Mock
    private ValidarSenhaUsuarioService validarSenhaUsuarioService;

    @Test
    void testExecute() {
        UsuarioInput usuarioInput = new UsuarioInput();
        usuarioInput.setEmail("usuario_teste@example.com");
        usuarioInput.setCpfCnpj("12345678910");
        usuarioInput.setSenha("senha456!");
        usuarioInput.setConfirmacaoSenha("senha456!");

        Logger logger = (Logger) LoggerFactory.getLogger(ValidadorCadastroUsuarioService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        doNothing().when(validarEmailUnicoService).isEmailUnique(any());
        doNothing().when(validarCpfCnpjUnicoService).isCpfCnpjUnique(any());
        doNothing().when(validarSenhaUsuarioService).isSenhaConfirmacaoEquals(any(), any());

        validadorCadastroUsuarioService.execute(usuarioInput);
        List<ILoggingEvent> logsList = listAppender.list;

        verify(validarEmailUnicoService, times(1)).isEmailUnique(any());
        verify(validarCpfCnpjUnicoService, times(1)).isCpfCnpjUnique(any());
        verify(validarSenhaUsuarioService, times(1)).isSenhaConfirmacaoEquals(any(), any());
        assertNotNull(logsList);
        assertEquals(Level.INFO, logsList.get(0).getLevel());
        assertEquals("As validações de isEmailUnique, isCpfCnpjUnique e isSenhaConfirmacaoEquals foram relizadas com sucesso.",
                logsList.get(0).getMessage());
    }

}
