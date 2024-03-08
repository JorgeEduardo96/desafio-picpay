package br.com.picpay.service;

import br.com.picpay.api.model.dto.ExtratoUsuarioDto;
import br.com.picpay.domain.model.Transferencia;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.SenhaIncorretaException;
import br.com.picpay.domain.model.exception.UsuarioNaoEncontradoException;
import br.com.picpay.domain.service.CadastroTransferenciaService;
import br.com.picpay.domain.service.CadastroUsuarioService;
import br.com.picpay.domain.service.ExtratoUsuarioService;
import br.com.picpay.domain.service.ValidarSenhaUsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExtratoUsuarioServiceTest {

    @Mock
    private CadastroUsuarioService cadastroUsuarioService;

    @Mock
    private ValidarSenhaUsuarioService validarSenhaUsuarioService;

    @Mock
    private CadastroTransferenciaService cadastroTransferenciaService;

    @InjectMocks
    private ExtratoUsuarioService extratoUsuarioService;

    @Test
    void testExtratoUsuario_whenInformacoesCorretas_success() {
        UUID usuarioId = UUID.randomUUID();
        UUID usuario2Id = UUID.randomUUID();
        UUID transferencia = UUID.randomUUID();
        UUID transferencia2 = UUID.randomUUID();

        String senhaUsuario = "senha123";
        Usuario usuario = buildUsuario(usuarioId);

        Usuario usuario2 = buildUsuario(usuario2Id);

        Transferencia transferenciaEnviada = Transferencia.builder()
                .id(transferencia2)
                .dataTransferencia(OffsetDateTime.of(2024, 1, 25, 0, 0, 0, 0, ZoneOffset.UTC))
                .usuarioTransferencia(usuario)
                .usuarioRecebimento(usuario2)
                .valorTransferencia(new BigDecimal("100"))
                .notificacaoEnviada(true)
                .build();

        Transferencia transferenciaRecebida = Transferencia.builder()
                .id(transferencia)
                .dataTransferencia(OffsetDateTime.of(2024, 1, 25, 0, 0, 0, 0, ZoneOffset.UTC))
                .usuarioTransferencia(usuario2)
                .usuarioRecebimento(usuario)
                .valorTransferencia(new BigDecimal("100"))
                .notificacaoEnviada(true)
                .build();

        when(cadastroUsuarioService.buscarOuFalhar(usuarioId)).thenReturn(usuario);
        when(cadastroTransferenciaService.getAllTransfereciasByUsuarioRecebimentoId(usuarioId)).thenReturn(List.of(transferenciaRecebida));
        when(cadastroTransferenciaService.getAllTransfereciasByUsuarioTransferecniaId(usuarioId)).thenReturn(List.of(transferenciaEnviada));
        doNothing().when(validarSenhaUsuarioService).isSenhaUsuarioCorreta(senhaUsuario, usuario.getSenha());

        ExtratoUsuarioDto extratoUsuarioDto = extratoUsuarioService.extratoUsuario(usuarioId, senhaUsuario);

        assertEquals(OffsetDateTime.now().getDayOfMonth(), extratoUsuarioDto.getDataExtrato().getDayOfMonth());
        assertEquals(1, extratoUsuarioDto.getRecebidas().size());
        assertEquals(BigDecimal.valueOf(100), extratoUsuarioDto.getTotalRecebidas());
        assertEquals(1, extratoUsuarioDto.getEnviadas().size());
        assertEquals(BigDecimal.valueOf(100), extratoUsuarioDto.getTotalEnviadas());
    }

    @Test
    public void testExtratoUsuario_whenUsuarioNaoExistente_throwsException() {
        UUID id = UUID.randomUUID();
        String senhaUsuario = "senha789!";

        when(cadastroUsuarioService.buscarOuFalhar(id)).thenThrow(new UsuarioNaoEncontradoException(id));

        assertThrows(UsuarioNaoEncontradoException.class, () -> extratoUsuarioService.extratoUsuario(id, senhaUsuario));
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
                () -> extratoUsuarioService.extratoUsuario(id, senhaUsuario));
    }

    private Usuario buildUsuario(UUID id) {
        return Usuario.builder()
                .id(id)
                .nomeCompleto("Usuário Teste")
                .senha("senha789!")
                .build();
    }

}
