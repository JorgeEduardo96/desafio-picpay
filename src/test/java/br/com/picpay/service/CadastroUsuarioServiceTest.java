package br.com.picpay.service;


import br.com.picpay.api.model.dto.UsuarioDto;
import br.com.picpay.api.model.input.UsuarioInput;
import br.com.picpay.domain.enumeration.TipoUsuario;
import br.com.picpay.domain.mapper.UsuarioMapper;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.repository.UsuarioRepository;
import br.com.picpay.domain.service.CadastroUsuarioService;
import br.com.picpay.domain.service.ValidarCpfCnpjUnicoService;
import br.com.picpay.domain.service.ValidarSenhaUsuarioService;
import br.com.picpay.domain.service.ValidarEmailUnicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastroUsuarioServiceTest {

    @InjectMocks
    private CadastroUsuarioService cadastroUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ValidarEmailUnicoService validarEmailUnicoService;

    @Mock
    private ValidarCpfCnpjUnicoService validarCpfCnpjUnicoService;

    @Mock
    private ValidarSenhaUsuarioService validarSenhaUsuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    void testSave_whenUsuarioGiven_returnsNewUsuario() {
        UUID id = UUID.randomUUID();
        UsuarioInput input = buildUsuarioInput();
        Usuario usuarioModel = buildUsuarioModel();
        Usuario usuarioModelResponse = buildUsuarioModel();
        usuarioModelResponse.setId(id);

        doNothing().when(validarEmailUnicoService).isEmailUnique(any());
        doNothing().when(validarCpfCnpjUnicoService).isCpfCnpjUnique(any());
        doNothing().when(validarSenhaUsuarioService).isSenhaConfirmacaoEquals(any(), any());
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

    private UsuarioDto buildUsuarioDto(UUID id) {
        Usuario usuarioModel = buildUsuarioModel();

        UsuarioDto usuarioDto = UsuarioDto.builder()
                .email(usuarioModel.getEmail())
                .tipoUsuario(usuarioModel.getTipoUsuario())
                .nomeCompleto(usuarioModel.getNomeCompleto())
                .cpfCnpj(usuarioModel.getCpfCnpj())
                .build();

        if (id != null) usuarioDto.setId(id);

        return usuarioDto;
    }


}
