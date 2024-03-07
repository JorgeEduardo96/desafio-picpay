package br.com.picpay.service;

import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.SenhaIncorretaException;
import br.com.picpay.domain.model.exception.UsuarioNaoEncontradoException;
import br.com.picpay.domain.service.CadastroUsuarioService;
import br.com.picpay.domain.service.CalculadorSaldoService;
import br.com.picpay.domain.service.SaldoUsuarioService;
import br.com.picpay.domain.service.ValidarSenhaUsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaldoUsuarioSerivceTest {

    @InjectMocks
    private SaldoUsuarioService saldoUsuarioService;

    @Mock
    private CalculadorSaldoService calculadorSaldoService;

    @Mock
    private CadastroUsuarioService cadastroUsuarioService;

    @Mock
    private ValidarSenhaUsuarioService validarSenhaUsuarioService;

    @Test
    public void testSaldoUsuario_whenSaldoUsuarioIs50() {
        UUID id = UUID.randomUUID();
        Usuario usuarioMock = buildUsuario(id);
        String senhaUsuario = "senha789!";
        String expectedResponse = "Olá Usuário Teste, seu saldo atual é: R$ 50,00";

        when(cadastroUsuarioService.buscarOuFalhar(id)).thenReturn(usuarioMock);
        doNothing().when(validarSenhaUsuarioService).isSenhaUsuarioCorreta(senhaUsuario, usuarioMock.getSenha());
        when(calculadorSaldoService.calcularSaldo(id)).thenReturn(new BigDecimal("50"));

        String actualResponse = saldoUsuarioService.saldoUsuario(id, senhaUsuario);

        assertNotNull(actualResponse);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void testSaldoUsuario_whenUsuarioNaoEncontrado() {
        UUID id = UUID.randomUUID();
        String senhaUsuario = "senha789!";

        when(cadastroUsuarioService.buscarOuFalhar(id)).thenThrow(new UsuarioNaoEncontradoException(id));

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> saldoUsuarioService.saldoUsuario(id, senhaUsuario));
    }

    @Test
    public void testSaldoUsuario_whenSenhaIncorreta_throwsException() {
        UUID id = UUID.randomUUID();
        Usuario usuarioMock = buildUsuario(id);
        String senhaUsuario = "senhaIncorreta";

        when(cadastroUsuarioService.buscarOuFalhar(id)).thenReturn(usuarioMock);
        doThrow(new SenhaIncorretaException("Senha digitada não está correta!")).when(validarSenhaUsuarioService)
                .isSenhaUsuarioCorreta(senhaUsuario, usuarioMock.getSenha());

        assertThrows(SenhaIncorretaException.class,
                () -> saldoUsuarioService.saldoUsuario(id, senhaUsuario));
    }

    private Usuario buildUsuario(UUID id) {
        return Usuario.builder()
                .id(id)
                .nomeCompleto("Usuário Teste")
                .senha("senha789!")
                .build();
    }


}
