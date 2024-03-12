package br.com.picpay.api.model.input;

import br.com.picpay.domain.enumeration.TipoUsuario;
import br.com.picpay.domain.validator.CpfCnpj;
import br.com.picpay.domain.validator.SegurancaSenha;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioInput {

    @NotBlank
    private String nomeCompleto;

    @NotBlank
    @CpfCnpj
    private String cpfCnpj;

    @NotBlank
    @Email
    private String email;

    @SegurancaSenha
    @NotBlank
    private String senha;

    @NotBlank
    private String confirmacaoSenha;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

}
