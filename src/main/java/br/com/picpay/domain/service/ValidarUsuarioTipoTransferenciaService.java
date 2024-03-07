package br.com.picpay.domain.service;

import br.com.picpay.domain.enumeration.TipoTransferencia;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.UsuarioNaoAptoTipoTransferenciaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidarUsuarioTipoTransferenciaService {

    public void isUsuarioAptoTipoTransferencia(Usuario usuarioModel, TipoTransferencia tipoTransferenciaUsuario) {
        if (usuarioModel.getTipoUsuario().getTiposTransferenciaPermitidas().stream().noneMatch(
                tipoTransferencia -> tipoTransferencia.equals(tipoTransferenciaUsuario)
        )) throw new UsuarioNaoAptoTipoTransferenciaException(usuarioModel.getNomeCompleto(), tipoTransferenciaUsuario);
    }

}
