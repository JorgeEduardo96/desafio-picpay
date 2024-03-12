package br.com.picpay.service;


import br.com.picpay.api.model.input.UsuarioInput;
import br.com.picpay.domain.enumeration.TipoUsuario;
import br.com.picpay.domain.mapper.UsuarioMapper;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.UsuarioNaoEncontradoException;
import br.com.picpay.domain.repository.UsuarioRepository;
import br.com.picpay.domain.service.CadastroUsuarioService;
import br.com.picpay.domain.service.ValidadorCadastroUsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastroUsuarioServiceTest {

    @InjectMocks
    private CadastroUsuarioService cadastroUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ValidadorCadastroUsuarioService validadorCadastroUsuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    void testSave_whenUsuarioGiven_returnsNewUsuario() {
        UUID id = UUID.randomUUID();
        UsuarioInput input = buildUsuarioInput();
        Usuario usuarioModel = buildUsuarioModel();
        Usuario usuarioModelResponse = buildUsuarioModel();
        usuarioModelResponse.setId(id);

        doNothing().when(validadorCadastroUsuarioService).execute(any());
        when(usuarioMapper.toModel(input)).thenReturn(usuarioModel);
        when(usuarioRepository.save(usuarioModel)).thenReturn(usuarioModelResponse);

        Usuario actualResponse = cadastroUsuarioService.save(input);

        verify(usuarioRepository, times(1)).save(any());
        assertNotNull(actualResponse);
        assertEquals(actualResponse.getId(), id);
        assertEquals(actualResponse.getNomeCompleto(), input.getNomeCompleto());
        assertEquals(actualResponse.getCpfCnpj(), input.getCpfCnpj());
    }

    private UsuarioInput buildUsuarioInput() {
        return new UsuarioInput("João Teste",
                "94521162002",
                "joao_teste@hotmail.com",
                "senhaTeste123!;",
                "senhaTeste123!;",
                TipoUsuario.COMUM);
    }

    private Usuario buildUsuarioModel() {
        UsuarioInput input = buildUsuarioInput();
        return Usuario.builder()
                .email(input.getEmail())
                .senha(input.getSenha())
                .tipoUsuario(input.getTipoUsuario())
                .nomeCompleto(input.getNomeCompleto())
                .cpfCnpj(input.getCpfCnpj())
                .build();
    }

    @Test
    void testBuscarOuFalhar_UsuarioEncontrado() {
        UUID usuarioId = UUID.randomUUID();
        Usuario expectedResult = Usuario.builder()
                .id(usuarioId)
                .build();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(expectedResult));

        Usuario actualResult = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testBuscarOuFalhar_whenUsuarioNaoEncontrado_throwsException() {
        UUID usuarioId = UUID.randomUUID();
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> cadastroUsuarioService.buscarOuFalhar(usuarioId));

        assertThatThrownBy(() -> cadastroUsuarioService.buscarOuFalhar(usuarioId))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessage(format("Usuário de ID: %s, não existente.", usuarioId));
    }

    @Test
    void testGetAll() {
        UUID usuarioId = UUID.randomUUID();
        UUID usuarioId2 = UUID.randomUUID();
        List<Usuario> usuariosMock = List.of(
                Usuario.builder()
                        .id(usuarioId)
                        .build(),
                Usuario.builder()
                        .id(usuarioId2)
                        .build()
        );

        when(usuarioRepository.findAll()).thenReturn(usuariosMock);

        List<Usuario> resultado = cadastroUsuarioService.getAll();
        assertEquals(usuariosMock, resultado);
    }

}
