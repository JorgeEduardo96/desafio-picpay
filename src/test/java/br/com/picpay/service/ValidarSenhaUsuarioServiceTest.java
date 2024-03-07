package br.com.picpay.service;

import br.com.picpay.domain.model.exception.SenhaIncorretaException;
import br.com.picpay.domain.service.ValidarSenhaUsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ValidarSenhaUsuarioServiceTest {

    @InjectMocks
    private ValidarSenhaUsuarioService validarSenhaUsuarioService;

    @Test
    void testIsSenhaConfirmacaoEquals_whenSenhasDiferentes_throwsException() {
        String senha = "senha";
        String confirmacaoSenha = "confirmacaoSenha";

        SenhaIncorretaException exception = assertThrows(SenhaIncorretaException.class, () -> {
            validarSenhaUsuarioService.isSenhaConfirmacaoEquals(senha, confirmacaoSenha);
        });

        assertEquals("Senhas digitadas não coincidem!", exception.getMessage());
    }

    @Test
    void testIsSenhaUsuarioCorreta_whenSenhasDiferentes_throwsException() {
        String senhaDto = "senhaDto";
        String senhaUsuarioModel = "senhaUsuarioModel";

        SenhaIncorretaException exception = assertThrows(SenhaIncorretaException.class, () -> {
            validarSenhaUsuarioService.isSenhaUsuarioCorreta(senhaDto, senhaUsuarioModel);
        });

        assertEquals("Senha digitada não está correta!", exception.getMessage());
    }


}
