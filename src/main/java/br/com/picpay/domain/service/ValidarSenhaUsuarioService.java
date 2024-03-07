package br.com.picpay.domain.service;

import br.com.picpay.domain.model.exception.SenhaIncorretaException;
import org.springframework.stereotype.Service;

@Service
public class ValidarSenhaUsuarioService {

    public void isSenhaConfirmacaoEquals(String senha, String confirmacaoSenha) {
        if (!senha.equalsIgnoreCase(confirmacaoSenha)) throw new SenhaIncorretaException("Senhas digitadas não coincidem!");
    }

    public void isSenhaUsuarioCorreta(String senhaDto, String senhaUsuarioModel) {
        if (!senhaDto.equalsIgnoreCase(senhaUsuarioModel)) throw new SenhaIncorretaException("Senha digitada não está correta!");
    }

}
