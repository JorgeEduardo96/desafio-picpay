package br.com.picpay.service;

import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.CpfCnpjDuplicadoException;
import br.com.picpay.domain.model.exception.EmailDuplicadoException;
import br.com.picpay.domain.repository.UsuarioRepository;
import br.com.picpay.domain.service.ValidarCpfCnpjUnicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidarCpfCnpjUnicoServiceTest {

    @InjectMocks
    private ValidarCpfCnpjUnicoService validarCpfCnpjUnicoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void testIsCpfCnpjUnique_whenNewCpfCnpj_doesNothing() {
        when(usuarioRepository.findByCpfCnpj(any())).thenReturn(Optional.empty());

        validarCpfCnpjUnicoService.isCpfCnpjUnique("12345678901");
    }

    @Test
    void testIsCpfCnpjUnique_whenNotUnique_throwsException() {
        when(usuarioRepository.findByCpfCnpj(any())).thenReturn(Optional.of(mock(Usuario.class)));

        assertThatThrownBy(() -> validarCpfCnpjUnicoService.isCpfCnpjUnique("12345678901"))
                .isInstanceOf(CpfCnpjDuplicadoException.class)
                .hasMessage("O CPF/CNPJ '12345678901' já está cadastrado!");
    }


}
