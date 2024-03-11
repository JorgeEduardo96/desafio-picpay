package br.com.picpay.domain.service;

import br.com.picpay.api.model.input.UsuarioInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidadorCadastroUsuarioService {

    private final ValidarEmailUnicoService validarEmailUnicoService;
    private final ValidarCpfCnpjUnicoService validarCpfCnpjUnicoService;
    private final ValidarSenhaUsuarioService validarSenhaUsuarioService;


    public void execute(UsuarioInput input) {
        this.validarEmailUnicoService.isEmailUnique(input.getEmail());
        this.validarCpfCnpjUnicoService.isCpfCnpjUnique(input.getCpfCnpj());
        this.validarSenhaUsuarioService.isSenhaConfirmacaoEquals(input.getSenha(), input.getConfirmacaoSenha());
        log.info("As validações de isEmailUnique, isCpfCnpjUnique e isSenhaConfirmacaoEquals foram relizadas com sucesso.");
    }

}
