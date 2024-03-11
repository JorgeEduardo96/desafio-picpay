package br.com.picpay.domain.service;

import br.com.picpay.api.model.input.UsuarioInput;
import br.com.picpay.domain.mapper.UsuarioMapper;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.UsuarioNaoEncontradoException;
import br.com.picpay.domain.repository.UsuarioRepository;
import br.com.picpay.util.CriptografiaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.picpay.util.CriptografiaUtil.criptografar;
import static br.com.picpay.util.RegexUtil.REGEX_APENAS_NUMEROS;
import static br.com.picpay.util.RegexUtil.replaceCaracteresComRegex;

@Service
@RequiredArgsConstructor
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ValidadorCadastroUsuarioService validadorCadastroUsuarioService;
    private final UsuarioMapper usuarioMapper;
    @Value("${chave.criptografia}")
    private int chaveCriptografia;

    public Usuario save(UsuarioInput input) {
        input.setCpfCnpj(replaceCaracteresComRegex(input.getCpfCnpj(), REGEX_APENAS_NUMEROS));
        validadorCadastroUsuarioService.execute(input);
        Usuario usuarioModel = usuarioMapper.toModel(input);
        usuarioModel.setSenha(criptografar(input.getSenha(), chaveCriptografia));
        return this.usuarioRepository.save(usuarioModel);
    }

    public Usuario buscarOuFalhar(UUID id) {
        return usuarioRepository.
                findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

}
