package br.com.picpay.api.model.dto;

import br.com.picpay.domain.enumeration.TipoUsuario;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class UsuarioDto {

    private UUID id;
    private String nomeCompleto;
    private String email;
    private TipoUsuario tipoUsuario;
    private String cpfCnpj;
    private OffsetDateTime dataCadastro;
}
