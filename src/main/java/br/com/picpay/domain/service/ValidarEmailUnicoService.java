package br.com.picpay.domain.service;

import br.com.picpay.domain.model.exception.EmailDuplicadoException;
import br.com.picpay.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidarEmailUnicoService {

    private final UsuarioRepository usuarioRepository;

    public void isEmailUnique(String email) {
        if (this.usuarioRepository.findByEmail(email).isPresent()) throw new EmailDuplicadoException(email);
    }

}
