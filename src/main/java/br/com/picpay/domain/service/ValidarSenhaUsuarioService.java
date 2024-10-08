package br.com.picpay.domain.service;

import br.com.picpay.domain.model.exception.SenhaIncorretaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static br.com.picpay.util.CriptografiaUtil.criptografar;

@Service
public class ValidarSenhaUsuarioService {

    @Value("${chave.criptografia}")
    private int chaveCriptografia;

    public void isSenhaConfirmacaoEquals(String senha, String confirmacaoSenha) {
        if (!senha.equalsIgnoreCase(confirmacaoSenha)) throw new SenhaIncorretaException("Senhas digitadas não coincidem!");
    }

    public void isSenhaUsuarioCorreta(String senhaDto, String senhaUsuarioModel) {
        if (!criptografar(senhaDto, chaveCriptografia).equalsIgnoreCase(senhaUsuarioModel)) throw new SenhaIncorretaException("Senha digitada não está correta!");
    }

}
