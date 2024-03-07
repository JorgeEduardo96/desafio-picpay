package br.com.picpay.api.model.input;

import br.com.picpay.api.model.dto.UsuarioIdDto;
import br.com.picpay.domain.enumeration.TipoTransferencia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferenciaInput {

    @NotNull
    private UsuarioIdDto usuarioTransferencia;

    @NotNull
    private UsuarioIdDto usuarioRecebimento;

    @Positive
    private BigDecimal valorTransferencia;

}
