package br.com.picpay.domain.service;

import br.com.picpay.domain.model.exception.CpfCnpjDuplicadoException;
import br.com.picpay.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidarCpfCnpjUnicoService {

    private final UsuarioRepository usuarioRepository;

    public void isCpfCnpjUnique(String cpfCnpj) {
        if (this.usuarioRepository.findByCpfCnpj(cpfCnpj).isPresent()) throw new CpfCnpjDuplicadoException(cpfCnpj);
    }

}
