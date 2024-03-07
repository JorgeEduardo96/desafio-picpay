package br.com.picpay.domain.service;

import br.com.picpay.api.model.input.UsuarioInput;
import br.com.picpay.domain.mapper.UsuarioMapper;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.UsuarioNaoEncontradoException;
import br.com.picpay.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.picpay.util.RegexUtil.REGEX_APENAS_NUMEROS;
import static br.com.picpay.util.RegexUtil.replaceCaracteresComRegex;

@Service
@RequiredArgsConstructor
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ValidarEmailUnicoService validarEmailUnicoService;
    private final ValidarCpfCnpjUnicoService validarCpfCnpjUnicoService;
    private final ValidarSenhaUsuarioService validarSenhaUsuarioService;
    private final UsuarioMapper usuarioMapper;

    public Usuario save(UsuarioInput input) {
        input.setCpfCnpj(replaceCaracteresComRegex(input.getCpfCnpj(), REGEX_APENAS_NUMEROS));

        this.validarEmailUnicoService.isEmailUnique(input.getEmail());
        this.validarCpfCnpjUnicoService.isCpfCnpjUnique(input.getCpfCnpj());
        this.validarSenhaUsuarioService.isSenhaConfirmacaoEquals(input.getSenha(), input.getConfirmacaoSenha());

        //TODO criar algum mecanismo de criptografia de senha

        return this.usuarioRepository.save(usuarioMapper.toModel(input));
    }

    public Usuario buscarOuFalhar(UUID id) {
        return usuarioRepository.
                findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

}
