package br.com.picpay.service;

import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.CpfCnpjDuplicadoException;
import br.com.picpay.domain.model.exception.EmailDuplicadoException;
import br.com.picpay.domain.repository.UsuarioRepository;
import br.com.picpay.domain.service.ValidarEmailUnicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidarEmailUnicoServiceTest {

    @InjectMocks
    private ValidarEmailUnicoService validarEmailUnicoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void testIsEmailUnique_whenNewEmail_doesNothing() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());

        validarEmailUnicoService.isEmailUnique("joao_teste@hotmail.com");
    }

    @Test
    void testIsCpfCnpjUnique_whenNotUnique_throwsException() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(mock(Usuario.class)));

        assertThatThrownBy(() -> validarEmailUnicoService.isEmailUnique("joao_teste@hotmail.com"))
                .isInstanceOf(EmailDuplicadoException.class)
                .hasMessage("O e-mail 'joao_teste@hotmail.com' já está cadastrado!");
    }


}
