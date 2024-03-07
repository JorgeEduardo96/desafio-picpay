package br.com.picpay.service;

import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.SenhaIncorretaException;
import br.com.picpay.domain.model.exception.UsuarioNaoAptoTipoTransferenciaException;
import br.com.picpay.domain.service.ValidarSenhaUsuarioService;
import br.com.picpay.domain.service.ValidarUsuarioTipoTransferenciaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.picpay.domain.enumeration.TipoTransferencia.TRANSFERIR;
import static br.com.picpay.domain.enumeration.TipoUsuario.LOJISTA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ValidarUsuarioTipoTransferenciaServiceTest {

    @InjectMocks
    private ValidarUsuarioTipoTransferenciaService validarUsuarioTipoTransferenciaService;

    @Test
    void testIsUsuarioAptoTipoTransferencia_whenUsuarioLojistaTipoTransferenciaTransferir_throwsException() {

        UsuarioNaoAptoTipoTransferenciaException exception = assertThrows(UsuarioNaoAptoTipoTransferenciaException.class, () -> {
            validarUsuarioTipoTransferenciaService.isUsuarioAptoTipoTransferencia(buildUsuario(), TRANSFERIR);
        });

        assertEquals("Usuário Teste não está apto para transferências do tipo: Transferir", exception.getMessage());
    }

    private Usuario buildUsuario() {
        return Usuario.builder()
                .nomeCompleto("Usuário Teste")
                .tipoUsuario(LOJISTA)
                .build();
    }

}
